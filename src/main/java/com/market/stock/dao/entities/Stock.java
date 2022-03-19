package com.market.stock.dao.entities;



import com.market.stock.common.CommonUtility;
import com.market.stock.common.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.util.Date;


@Document(collection="stock")
public class Stock {
    @Id
    private String Id;
    @Field(name="companycode")
    private String companyCode;
    @Field(name="stockprice")
    private double price;
    @Field(name="createdDate")
    @CreatedDate
    private Date createdDate;
    @Field(name="createdTime")
    private String createdTime;



    @PersistenceConstructor
    public Stock(String companyCode)  {
        this.companyCode = companyCode;

    }
    public Stock(String companyCode, double price)  {
        this.companyCode = companyCode;
        this.price = price;
        DateTime datetime= CommonUtility.ExtractDateTime(new Timestamp(System.currentTimeMillis()));
        this.createdDate =datetime.getDate();
        this.createdTime=datetime.getTime();
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
