package com.wifi.yilong.yilongwifi.Model;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/23.
 */

public class User implements Serializable {

    public static final String JSON_ID = "_id";
    public static final String JSON_EMAIL = "email";
    public static final String JSON_NAME = "name";
    public static final String JSON_EXPIRATION = "exp";
    public static final String JSON_IAT = "iat";
    public static final String JSON_TOKEN = "token";
    public static final String USER = "user";
    String id;
    String email;
    String name = "default";
    Date expiration;
    String token = "default";


    public User(){

    }
    public User(String _id , String _name , String _email , Date _ex , String _token){
        id = _id;
        email = _email;
        name = _name;
        expiration = _ex;
        token = _token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

}
