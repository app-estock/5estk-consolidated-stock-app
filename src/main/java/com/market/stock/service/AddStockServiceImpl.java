package com.market.stock.service;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockResponse;
import com.market.stock.common.AppConstants;
import com.market.stock.common.headers.Headers;
import com.market.stock.common.logging.TransactionLog;
import com.market.stock.dao.entities.Stock;
import com.market.stock.dao.StockRepository;
import com.market.stock.dao.sac.UpdateStockService;
import com.market.stock.domain.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddStockServiceImpl implements AddStockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UpdateStockService updateStockServiceSac;

    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(FetchStocksServiceImpl.class);
    private TransactionLog transactionLog;
    @Override
    public StockResponse processRequest(StockMapper stockMapper, Headers requestHeaders,Map<String,String> passRequestHeaders) {
        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("StocksV1", "addStocksV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();

        //region transaction log population
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog(requestHeaders.toString());
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        //endregion
        try {
            Stock s = stockRepository.save(new Stock(stockMapper.getCode(), stockMapper.getStockPrice()));
            if ( updateStockServiceSac.updateStockService(stockMapper,passRequestHeaders)) {
                   extendedProperties.put("Saved Record:",s.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    return new StockResponse(true);
                }
            extendedProperties.put("Saved Record:","None");
             transactionLog.setStatus(AppConstants.FAIL);
            return new StockResponse(false);
        }
    catch(Exception e)
    {
        throw new RuntimeException(e);
    }
        finally{
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
        }
    }
}
