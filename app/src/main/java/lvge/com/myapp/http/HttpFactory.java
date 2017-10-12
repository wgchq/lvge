package lvge.com.myapp.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import org.reactivestreams.Publisher;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lvge.com.myapp.BuildConfig;
import lvge.com.myapp.MyApplication;
import lvge.com.myapp.model.base.BaseResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpFactory {
    private static HttpFactory instance;
    private LinkedHashMap<String,Retrofit> retrofitCache;

    private HttpFactory(){
        retrofitCache = new LinkedHashMap<>(10);
    }
    public static HttpFactory getInstance(){
        if (instance == null){
            synchronized (HttpFactory.class){
                if (instance == null){
                    return new HttpFactory();
                }
            }
        }
        return instance;
    }
    private static OkHttpClient getOkHttpClient(){

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            //添加header
//            okHttpBuilder.addInterceptor(new HeaderInterceptor());
        okHttpBuilder.cookieJar(MyApplication.getInstance().cookieJar1);
        okHttpBuilder.cookieJar(MyApplication.getInstance().cookieJar2);
        okHttpBuilder.connectTimeout(15, TimeUnit.SECONDS);
            //添加请求超时一个拦截器，用于处理请求超时的时候的错误信息，必须在HeaderInterceptor拦截器后面
            if (BuildConfig.DEBUG){
                okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addNetworkInterceptor(interceptor);
            }
            //添加log拦截器

        OkHttpClient okHttpClient = okHttpBuilder.build();

        return okHttpClient;

    }
    public Retrofit build(String url) {
        Retrofit retrofit = retrofitCache.get(url);

        if(retrofit != null)
            return retrofit;
        synchronized (Retrofit.class){
            retrofit = retrofitCache.get(url);
            if (retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .client(getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                retrofitCache.put(url,retrofit);
            }
            return retrofit;
        }
    }
    public static <T> FlowableTransformer<BaseResponse<T>,T> applaySchedulers(){
        return (FlowableTransformer<BaseResponse<T>,T>)TRANSFORMER;
    }
    static final FlowableTransformer TRANSFORMER = new FlowableTransformer() {
        @Override
        public Publisher apply(Flowable upstream) {
            return upstream.subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function() {
                        @Override
                        public Flowable apply(Object o) throws Exception {
                            return flatResponse((BaseResponse)o);
                        }
                    });
        }

    };
    private static <T> Flowable<T> flatResponse(final BaseResponse<T> response) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                if (response.isSuccess()) {

                    if (!e.isCancelled()) {
                        e.onNext(response.getMarketEntity() == null?response.getPageResult():response.getMarketEntity());
                    }
                } else {
                    if (!e.isCancelled()) {
                        e.onError(new APIException(response.getOperationResult().getResultCode(), response.getOperationResult().getResultMsg(),
                                response.getMarketEntity()));

                    }
                    return;
                }

                if (!e.isCancelled()) {
                    e.onComplete();
                }
            }
        }, BackpressureStrategy.LATEST);

    }
}
