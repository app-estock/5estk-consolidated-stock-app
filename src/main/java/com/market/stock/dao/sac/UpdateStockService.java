package com.market.stock.dao.sac;

import com.market.stock.domain.mapper.StockMapper;

import java.util.Map;

public interface UpdateStockService {

    public boolean updateStockService(StockMapper stockMapper, Map<String,String> passRequestHeaders);
}
