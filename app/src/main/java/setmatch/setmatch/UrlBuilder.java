package setmatch.setmatch;


public class UrlBuilder {
    public final static String baseUrl = "http://gablescode.net:8000/";
    public final static String registerEndpoint = "user";
    public final static String loginEndpoint = "login";
    public final static String profileEndpoint = "profile";

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static String getRegisterEndpoint(){
        return getBaseUrl() + registerEndpoint;
    }
    public static String getLoginEndpoint(){
        return getBaseUrl() + loginEndpoint;
    }
    public static String getProfileEndpoint(){
        return getBaseUrl() + profileEndpoint;
    }

}
