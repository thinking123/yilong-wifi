package com.wifi.yilong.yilongwifi.Http.rest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18.
 */
@Table(name = "OpeningTimes")
public class OpeningTime extends Model  implements Serializable {

    @Expose
    @Column(name="days")
    public String days;

    @Expose
    @Column(name="opening")
    public String opening;

    @Expose
    @Column(name="closing")
    public String closing;

    @Expose
    @Column(name="closed")
    public boolean closed;

    @Column(name="Location", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Location location;


    public OpeningTime(){
        super();
    }
}
