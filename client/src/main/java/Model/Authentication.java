package Model;

public class Authentication {
    private static String token;

    public Authentication(){ }

    public void setToken(String token){
        Authentication.token = token;
    }
    public static String getToken() {
        return token;
    }
}
