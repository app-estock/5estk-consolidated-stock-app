package com.market.stock.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Validated
@JsonRootName("CompanyRequest")
public class StockRequest implements Serializable {

    @JsonProperty("stockPrice")
    private  Double stockPrice;

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "StockRequest{" +
                "stockPrice:" + stockPrice +
                '}';
    }
}
