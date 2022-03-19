package com.market.stock.service;

import com.market.stock.api.StockResponse;
import com.market.stock.exception.NoDataFoundException;

public interface DeleteStocksService {
    StockResponse processRequest(String companyCode) throws NoDataFoundException;
}
