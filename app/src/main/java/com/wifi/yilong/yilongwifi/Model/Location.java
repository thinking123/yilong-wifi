package com.wifi.yilong.yilongwifi.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class Location implements Serializable{
    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public static final String DISTANCE = "distance";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String RATING = "rating";
    public static final String ID = "_id";
    public static final String FACILITIES = "facilities";

    public static final String OPENINGTIMES = "openingTimes";
    public static final String REVIEWS = "reviews";
    public static final String COORDS = "coords";

    float distance;
    String name;
    String address;
    int rating;
    String id;
    List<String> facilities;
    List<OpeningTime> openingTimes;
    List<Review> reviews;
    //经度纬度
    float latitude;
    float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public List<OpeningTime> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<OpeningTime> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }




}
