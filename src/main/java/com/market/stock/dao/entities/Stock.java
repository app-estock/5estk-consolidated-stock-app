package com.market.stock.dao.entities;

import com.market.stock.common.CommonUtility;
import com.market.stock.common.DateTime;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="stock")
public class Stock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="companycode")
    private String companyCode;
    @Column(name="stockprice")
    private double price;
    @CreationTimestamp
    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="createdTime")
    private String createdTime;


    public Stock() {
        super();
    }


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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
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
