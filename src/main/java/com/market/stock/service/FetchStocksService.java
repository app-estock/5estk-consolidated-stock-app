package com.market.stock.service;

import com.market.stock.api.StockResponse;
import com.market.stock.common.headers.Headers;

import java.text.ParseException;
import java.util.Date;

public interface FetchStocksService {

    StockResponse processRequest(String companyCode, Date startDate, Date endDate, Headers requestHeaders) throws ParseException;

}
