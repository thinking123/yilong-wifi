package com.wifi.yilong.yilongwifi.Http.rest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */
@Table(name="Users" )
public class User extends Model  implements Serializable {
//    String id;
//    String email;
//    String name = "default";
//    Date expiration;
//    String token = "default";
    public static String USERID = "id";
    @Expose
    @SerializedName("_id")
    @Column(name="userId" , unique = true , onUniqueConflict = Column.ConflictAction.REPLACE )
    public String id;

    @Expose
    @SerializedName("iat")
    @Column(name="lastLogTime")
    public Date lastLogTime;

    @Expose
    @Column(name="email")
    public String email;

    @Expose
    @Column(name="name")
    public String name;

    @Expose
    @SerializedName("exp")
    @Column(name="expirationTokenTime")
    public Date expirationTokenTime;


    @Column(name="authToken")
    public String authToken;

    @Column(name = "password")
    public String password;

//    public List<Location> locations;

    public List<Location> getLocations(){
        return getMany(Location.class , "User");
    }
    public User(){
        super();
    }
}
