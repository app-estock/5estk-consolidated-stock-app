package com.market.stock.common;

import java.util.Date;

public class DateTime {
    private Date date;
    private String time;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DateTime(Date date, String time) {
        this.date = date;
        this.time = time;
    }
}
