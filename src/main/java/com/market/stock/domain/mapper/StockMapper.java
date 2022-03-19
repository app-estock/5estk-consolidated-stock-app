package com.market.stock.domain.mapper;

public class StockMapper {

    private String code;
    private double stockPrice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public StockMapper(String code, double stockPrice) {
        this.code = code;
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "StockMapper{" +
                "code='" + code + '\'' +
                ", stockPrice='" + stockPrice + '\'' +
                '}';
    }
}
