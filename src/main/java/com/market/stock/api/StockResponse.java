package com.market.stock.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Validated
@JsonRootName("stockesponse")
public class StockResponse implements Serializable {

    @JsonProperty("stockPriceDetailList")
    private List<StockPriceDetail> stockPriceDetailList;

    @JsonProperty("successIndicator")
    private Boolean successIndicator;

    @JsonProperty("maximumPrice")
    private double maximumPrice;
    @JsonProperty("minimumPrice")
    private double minimumPrice;
    @JsonProperty("averagePrice")
    private double averagePrice;

    @JsonProperty("errorMessages")
    private List<ErrorMessage> errorMessages;

    public List<StockPriceDetail> getStockPriceDetailList() {
        return stockPriceDetailList;
    }

    public void setStockPriceDetailList(List<StockPriceDetail> stockPriceDetailList) {
        this.stockPriceDetailList = stockPriceDetailList;
    }

    public Boolean getSuccessIndicator() {
        return successIndicator;
    }

    public void setSuccessIndicator(Boolean successIndicator) {
        this.successIndicator = successIndicator;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public StockResponse(List<StockPriceDetail> stockPriceDetailList, double maximumPrice, double minimumPrice, double averagePrice, List<ErrorMessage> errorMessages) {
        this.stockPriceDetailList = stockPriceDetailList;
        this.maximumPrice = maximumPrice;
        this.minimumPrice = minimumPrice;
        this.averagePrice = averagePrice;
        this.errorMessages = errorMessages;
    }
    public StockResponse()
    {

    }
    public StockResponse(Boolean successIndicator) {
        this.successIndicator = successIndicator;
    }

}
