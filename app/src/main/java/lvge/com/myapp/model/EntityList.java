package lvge.com.myapp.model;

/**
 * Created by mac on 2017/9/19.
 */

public  class EntityList{
    public String getNOTICE_MESSAGE() {
        return NOTICE_MESSAGE;
    }

    public void setNOTICE_MESSAGE(String NOTICE_MESSAGE) {
        this.NOTICE_MESSAGE = NOTICE_MESSAGE;
    }

    private String NOTICE_MESSAGE;
    private int RECEIVER_ID;
    private String CREATE_TIME;
    private int NOTICE_CONTENT_TYPE;
    private int RECEIVER_TYPE;
    private int CUSTOMER_NOTICE_ID;
    private int IS_SEND;
    private int IS_DELETE;
    private int SEND_STATUS;
    private int IS_READ;
    private int CREATEOR;
    private String NOTICE_TITLE;
    private int NOTICE_TYPE;
    private String END_TIME;
    private int NOTICE_ID;

    public int getRECEIVER_ID() {
        return RECEIVER_ID;
    }

    public void setRECEIVER_ID(int RECEIVER_ID) {
        this.RECEIVER_ID = RECEIVER_ID;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getNOTICE_CONTENT_TYPE() {
        return NOTICE_CONTENT_TYPE;
    }

    public void setNOTICE_CONTENT_TYPE(int NOTICE_CONTENT_TYPE) {
        this.NOTICE_CONTENT_TYPE = NOTICE_CONTENT_TYPE;
    }

    public int getRECEIVER_TYPE() {
        return RECEIVER_TYPE;
    }

    public void setRECEIVER_TYPE(int RECEIVER_TYPE) {
        this.RECEIVER_TYPE = RECEIVER_TYPE;
    }

    public int getCUSTOMER_NOTICE_ID() {
        return CUSTOMER_NOTICE_ID;
    }

    public void setCUSTOMER_NOTICE_ID(int CUSTOMER_NOTICE_ID) {
        this.CUSTOMER_NOTICE_ID = CUSTOMER_NOTICE_ID;
    }

    public int getIS_SEND() {
        return IS_SEND;
    }

    public void setIS_SEND(int IS_SEND) {
        this.IS_SEND = IS_SEND;
    }

    public int getIS_DELETE() {
        return IS_DELETE;
    }

    public void setIS_DELETE(int IS_DELETE) {
        this.IS_DELETE = IS_DELETE;
    }

    public int getSEND_STATUS() {
        return SEND_STATUS;
    }

    public void setSEND_STATUS(int SEND_STATUS) {
        this.SEND_STATUS = SEND_STATUS;
    }

    public int getIS_READ() {
        return IS_READ;
    }

    public void setIS_READ(int IS_READ) {
        this.IS_READ = IS_READ;
    }

    public int getCREATEOR() {
        return CREATEOR;
    }

    public void setCREATEOR(int CREATEOR) {
        this.CREATEOR = CREATEOR;
    }

    public String getNOTICE_TITLE() {
        return NOTICE_TITLE;
    }

    public void setNOTICE_TITLE(String NOTICE_TITLE) {
        this.NOTICE_TITLE = NOTICE_TITLE;
    }

    public int getNOTICE_TYPE() {
        return NOTICE_TYPE;
    }

    public void setNOTICE_TYPE(int NOTICE_TYPE) {
        this.NOTICE_TYPE = NOTICE_TYPE;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public int getNOTICE_ID() {
        return NOTICE_ID;
    }

    public void setNOTICE_ID(int NOTICE_ID) {
        this.NOTICE_ID = NOTICE_ID;
    }
}