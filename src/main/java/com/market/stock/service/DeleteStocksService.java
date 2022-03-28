package com.market.stock.service;

import com.market.stock.api.StockResponse;
import com.market.stock.common.headers.Headers;
import com.market.stock.exception.NoDataFoundException;

public interface DeleteStocksService {
    StockResponse processRequest(String companyCode, Headers requestHeaders) throws NoDataFoundException;
}
