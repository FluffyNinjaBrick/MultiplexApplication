package model;

import java.util.LinkedList;
import java.util.List;

public class Authentication {
    private static String token;
    private boolean isLoggedIn = false;
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String role){
        return roles.stream().filter(r -> r.equals(role)).findAny().orElse(null) != null;
    }
    public void clearRoles(){
        roles.clear();
    }

    public  void addRole(String role){
        roles.add(role);
    }
    public Authentication(){
        roles = new LinkedList<>();
    }

    public void setToken(String token){
        Authentication.token = token;
        isLoggedIn = true;
    }
    public String getToken() {
        return token;
    }

    public void clear(){
        this.isLoggedIn = false;
        this.setToken("");
        this.clearRoles();
    }
}
