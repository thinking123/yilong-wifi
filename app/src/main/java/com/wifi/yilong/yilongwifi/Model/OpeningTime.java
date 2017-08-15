package com.wifi.yilong.yilongwifi.Model;

import com.wifi.yilong.yilongwifi.Infrastructure.Utils;

/**
 * Created by Administrator on 2017/1/27.
 */

public class OpeningTime {
    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public OpeningTime(boolean closed, String closing, String days, String opening) {
        this.closed = closed;
        this.closing = closing;
        this.days = days;
        this.opening = opening;
    }

    public OpeningTime(){

    }
    String days;
    String opening;
    String closing;
    boolean closed;

    public static final String DAYS = "days";
    public static final String OPENING = "opening";
    public static final String CLOSING = "closing";
    public static final String CLOSED = "closed";

    @Override
    public String toString() {
//        return super.toString();
        if(Utils.IsStringEmpty(opening) || Utils.IsStringEmpty(closing)){
            return String.format("%s : %s" , days , closed ? "closed" : "open");
        }else{
            //Monday - Friday : 7:00am - 7:00pm
            return String.format("%s : %s - %s" , days , opening , closing);
        }
    }
}
