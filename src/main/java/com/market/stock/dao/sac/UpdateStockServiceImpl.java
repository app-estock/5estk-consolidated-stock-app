package com.market.stock.dao.sac;

import com.market.stock.api.StockRequest;
import com.market.stock.domain.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class UpdateStockServiceImpl  implements UpdateStockService{
    @Value("${EXT_CALL_ENDPOINT :http://localhost:8081/CompanyV1}")
    private String externalEndpoint;
    @Override
    public boolean updateStockService(StockMapper stockMapper, Map<String,String> requestHeaders) {

        String updateStockServiceV1Endpoint=externalEndpoint+"/update/"+stockMapper.getCode();

        StockRequest body = new StockRequest();
        body.setStockPrice(stockMapper.getStockPrice());

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.put(updateStockServiceV1Endpoint, body);

            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
