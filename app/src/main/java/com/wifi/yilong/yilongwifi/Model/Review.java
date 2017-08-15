package com.wifi.yilong.yilongwifi.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/27.
 */

public class Review implements Serializable{


    public Review(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public Review(String author, Date createdOn, String id, int rating, String reviewText, Date timestamp) {
        this.author = author;
        this.createdOn = createdOn;
        this.id = id;
        this.rating = rating;
        this.reviewText = reviewText;
        this.timestamp = timestamp;
    }

    String author;
    int rating;
    String reviewText;
    Date createdOn;
    String id;
    Date timestamp;
    public static final String AUTHOR = "author";
    public static final String RATING= "rting";
    public static final String REVIEWTEXT= "reviewText";
    public static final String CREATEDON= "createdOn";
    public static final String ID= "id";
    public static final String REVIEW = "review";
    public static final String TIMESTAMP = "timestamp";
}
