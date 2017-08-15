package com.wifi.yilong.yilongwifi.Http.rest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */
@Table(name = "Locations" )
public class Location extends Model implements Serializable{

    public static String LOCATION = "LOCATION";
//    List<OpeningTime> openingTimes;
//    List<Review> reviews;
    //经度纬度

    @Expose
    @Column(name="latitude")
    public float latitude;

    @Expose
    @Column(name="longitude")
    public float longitude;

    @Expose
    @Column(name="distance")
    public float distance;

    @Expose
    @Column(name="name")
    public String name;

    @Expose
    @Column(name="address")
    public String address;

    @Expose
    @Column(name="rating")
    public int rating;

    @Expose
    @Column(name="locationId" , unique = true , onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("_id")
    public String id;

    @Expose
    @Column(name="facilities")
    public List<String> facilities = new ArrayList<>();

    //foreign key
    @Column(name = "User")
    public User user;


//    public List<Review> reviews;

    @Expose
    @SerializedName("openingTimes")
    public List<OpeningTime> openingTimes;

    @Expose
    @SerializedName("reviews")
    public List<Review> reviews;

    public List<Review> getReviews(){
//      return   new Select().from(Review.class).where("Location = ?" , this.getId()).execute();
        return getMany(Review.class , "Location");
    }

//    public List<OpeningTime> openingTimes;

    public List<OpeningTime> getOpeningTimes(){
        return getMany(OpeningTime.class , "Location");
    }

    public Location(){
        super();
    }


}
