package setmatch.setmatch;


public class UrlBuilder {
    public final static String baseUrl = "http://gablescode.net:8000/";
    public final static String registerEndpoint = "user";
    public final static String loginEndpoint = "login";
    public final static String profileEndpoint = "profile";
    public final static String editProfileEndpoint = "profile/edit";
    public final static String matchEndpoint = "match";
    public final static String checkEndpoint = "check";

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static String getRegisterEndpoint(){
        return getBaseUrl() + registerEndpoint;
    }
    public static String getLoginEndpoint(){
        return getBaseUrl() + loginEndpoint;
    }
    public static String getMatchEndpoint(){ return getBaseUrl() + matchEndpoint;}
    public static String getCheckEndpoint(){ return getBaseUrl() + checkEndpoint;}
    public static String getProfileEndpoint(){
        return getBaseUrl() + profileEndpoint;
    }
    public static String getEditProfileEndpoint(){
        return getBaseUrl() + editProfileEndpoint;
    }
}
