package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/9/16.
 */

public  class  LoadRightSideMode {


    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    private OperationResult operationResult;

    public  String getPageResult() {
        return pageResult;
    }

    public void setPageResult(String pageResult) {
        this.pageResult = pageResult;
    }

    private  String pageResult;

    public MarketEntity_right getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(MarketEntity_right marketEntity) {
        this.marketEntity = marketEntity;
    }

    private  MarketEntity_right marketEntity;

    public class MarketEntity_right{


        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        private int sex;


        public int getPost() {
            return post;
        }

        public void setPost(int post) {
            this.post = post;
        }

        private int post;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        private String NAME;

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        private String USER_ID;

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }

        private String IP;

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        private String job;

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        private String STATUS;

        public Seller getSeller() {
            return seller;
        }

        public void setSeller(Seller seller) {
            this.seller = seller;
        }

        private Seller seller;

        public class Seller{

            public List<SellerImgs> getSellerImgs() {
                return sellerImgs;
            }

            public void setSellerImgs(List<SellerImgs> sellerImgs) {
                this.sellerImgs = sellerImgs;
            }

            private List<SellerImgs> sellerImgs;

            public String getAreaID() {
                return areaID;
            }

            public void setAreaID(String areaID) {
                this.areaID = areaID;
            }

            private String areaID;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            private String createTime;

            public int getInsuranceCompanyID() {
                return insuranceCompanyID;
            }

            public void setInsuranceCompanyID(int insuranceCompanyID) {
                this.insuranceCompanyID = insuranceCompanyID;
            }

            private int insuranceCompanyID;

            public int getIsInstallShop() {
                return isInstallShop;
            }

            public void setIsInstallShop(int isInstallShop) {
                this.isInstallShop = isInstallShop;
            }

            private int isInstallShop;

            public String getMainImgPath() {
                return mainImgPath;
            }

            public void setMainImgPath(String mainImgPath) {
                this.mainImgPath = mainImgPath;
            }

            private String mainImgPath;

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            private String lng;


            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            private int type;

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            private String companyName;

            public int getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(int checkStatus) {
                this.checkStatus = checkStatus;
            }

            private int checkStatus;

            public int getIsOpenService() {
                return isOpenService;
            }

            public void setIsOpenService(int isOpenService) {
                this.isOpenService = isOpenService;
            }

            private int isOpenService;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            private String address;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String name;

            public int getOpenServiceStatus() {
                return openServiceStatus;
            }

            public void setOpenServiceStatus(int openServiceStatus) {
                this.openServiceStatus = openServiceStatus;
            }

            private int openServiceStatus;

            public int getNoExpense() {
                return noExpense;
            }

            public void setNoExpense(int noExpense) {
                this.noExpense = noExpense;
            }

            private int noExpense;

            public int getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(int seller_id) {
                this.seller_id = seller_id;
            }

            private int seller_id;

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            private String telephone;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            private String lat;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            private String mobile;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        private String PHONE;

        public SellerDepartment getSellerDepartment() {
            return sellerDepartment;
        }

        public void setSellerDepartment(SellerDepartment sellerDepartment) {
            this.sellerDepartment = sellerDepartment;
        }

        private SellerDepartment sellerDepartment;

        public class SellerDepartment {
            public int getSellerDepartment_ID() {
                return sellerDepartment_ID;
            }

            public void setSellerDepartment_ID(int sellerDepartment_ID) {
                this.sellerDepartment_ID = sellerDepartment_ID;
            }

            private int sellerDepartment_ID;

            public int getSeller_ID() {
                return seller_ID;
            }

            public void setSeller_ID(int seller_ID) {
                this.seller_ID = seller_ID;
            }

            private int seller_ID;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String name;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        private String headImg;

        public String getLAST_LOGIN() {
            return LAST_LOGIN;
        }

        public void setLAST_LOGIN(String LAST_LOGIN) {
            this.LAST_LOGIN = LAST_LOGIN;
        }

        private String LAST_LOGIN;

        public String getAppRights() {
            return appRights;
        }

        public void setAppRights(String appRights) {
            this.appRights = appRights;
        }

        private String appRights;

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }

        private Page page;
        public class Page{
            public Pd getPd() {
                return pd;
            }

            public void setPd(Pd pd) {
                this.pd = pd;
            }

            private Pd pd;

            public class Pd{

            }

            public String getPageStr() {
                return pageStr;
            }

            public void setPageStr(String pageStr) {
                this.pageStr = pageStr;
            }

            private String pageStr;

            public int getTotalResult() {
                return totalResult;
            }

            public void setTotalResult(int totalResult) {
                this.totalResult = totalResult;
            }

            private int totalResult;

            public int getShowCount() {
                return showCount;
            }

            public void setShowCount(int showCount) {
                this.showCount = showCount;
            }

            private int showCount;

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            private int currentPage;

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            private int totalPage;

            public int getCurrentResult() {
                return currentResult;
            }

            public void setCurrentResult(int currentResult) {
                this.currentResult = currentResult;
            }

            private int currentResult;

            public boolean isEntityOrField() {
                return entityOrField;
            }

            public void setEntityOrField(boolean entityOrField) {
                this.entityOrField = entityOrField;
            }

            private boolean entityOrField;
        }

        public String getEMAIL() {
            return EMAIL;
        }

        public void setEMAIL(String EMAIL) {
            this.EMAIL = EMAIL;
        }

        private String EMAIL;

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        private String USERNAME;

        public String getPASSWORD() {
            return PASSWORD;
        }

        public void setPASSWORD(String PASSWORD) {
            this.PASSWORD = PASSWORD;
        }

        private String PASSWORD;

        public String getSKIN() {
            return SKIN;
        }

        public void setSKIN(String SKIN) {
            this.SKIN = SKIN;
        }

        private String SKIN;
    }
}
