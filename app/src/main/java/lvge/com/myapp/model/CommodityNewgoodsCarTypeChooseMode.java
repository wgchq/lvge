package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/10/14.
 */

public class CommodityNewgoodsCarTypeChooseMode {
    private OperationResult operationResult;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public String getPageResult() {
        return pageResult;
    }

    public void setPageResult(String pageResult) {
        this.pageResult = pageResult;
    }

    public List<MarketEntity> getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(List<MarketEntity> marketEntity) {
        this.marketEntity = marketEntity;
    }

    private String pageResult;

    private List<MarketEntity> marketEntity;

    public class MarketEntity{
        private int id;
        private List<Series> series;
        public class Series{
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFirstLetter() {
                return firstLetter;
            }

            public void setFirstLetter(String firstLetter) {
                this.firstLetter = firstLetter;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String firstLetter;
            private String name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Series> getSeries() {
            return series;
        }

        public void setSeries(List<Series> series) {
            this.series = series;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String logo;
        private String firstLetter;
        private String name;
    }
}
