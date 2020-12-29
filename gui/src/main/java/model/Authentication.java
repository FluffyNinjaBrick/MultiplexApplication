package model;

public class Authentication {
    private static String token;
    private boolean isLoggedIn = false;
    public Authentication(){ }

    public void setToken(String token){
        Authentication.token = token;
        isLoggedIn = true;
    }
    public static String getToken() {
        return token;
    }
}
