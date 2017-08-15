package com.wifi.yilong.yilongwifi.Http.rest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/18.
 */
@Table(name = "Reviews" )
public class Review extends Model  implements Serializable {

    @Expose
    @Column(name="author")
    public String author;
    @Expose
    @Column(name="rating")
    @SerializedName("rting")
    public int rating;
    @Expose
    @Column(name="reviewText")
    public String reviewText;
    @Expose
    @Column(name="createdOn")
    public Date createdOn;
    @Expose
    @Column(name="reviewId")
    public String id;
    @Expose
    @Column(name="timestamp")
    public Date timestamp;

    @Column(name="Location", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Location location;

    public static  final  String REVIEW = "REVIEW";
    public Review(){
        super();
    }
}
