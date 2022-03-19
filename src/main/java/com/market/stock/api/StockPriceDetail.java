package com.market.stock.api;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Validated
@JsonRootName("stockPriceDetail")
public class StockPriceDetail {
    @JsonProperty("companycode")
    private String companycode;
    @JsonProperty("stockPrice")
    private Double stockPrice;
    @JsonProperty("stockDate")
    private Date stockDate;
    @JsonProperty("stockTime")
    private String stockTime;

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Date getStockDate() {
        return stockDate;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    public String getStockTime() {
        return stockTime;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public StockPriceDetail(String companycode, Double stockPrice, Date stockDate, String stockTime) {
        this.companycode = companycode;
        this.stockPrice = stockPrice;
        this.stockDate = stockDate;
        this.stockTime = stockTime;
    }
}
