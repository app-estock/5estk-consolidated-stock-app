package com.market.stock.service;

import com.market.stock.api.StockResponse;
import com.market.stock.common.headers.Headers;
import com.market.stock.domain.mapper.StockMapper;

import java.util.Map;

public interface AddStockService {

    StockResponse processRequest(StockMapper stockMapper, Headers headers, Map<String,String> passHeaders);
}
