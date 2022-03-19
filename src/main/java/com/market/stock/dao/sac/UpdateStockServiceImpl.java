package com.market.stock.dao.sac;

import com.market.stock.api.StockRequest;
import com.market.stock.domain.mapper.StockMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UpdateStockServiceImpl  implements UpdateStockService{
    @Override
    public boolean updateStockService(StockMapper stockMapper) {

        String updateStockServiceV1Endpoint="http://localhost:8081/CompanyV1/update/"+stockMapper.getCode();
        StockRequest stockRequest= new StockRequest();
        stockRequest.setStockPrice(stockMapper.getStockPrice());
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.put(updateStockServiceV1Endpoint, stockRequest);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
