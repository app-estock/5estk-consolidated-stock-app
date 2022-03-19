package com.market.stock.service;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockResponse;
import com.market.stock.common.headers.Headers;
import com.market.stock.common.logging.TransactionLog;
import com.market.stock.dao.entities.Stock;
import com.market.stock.dao.repository.StockRepository;
import com.market.stock.dao.sac.UpdateStockService;
import com.market.stock.domain.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public StockResponse processRequest(StockMapper stockMapper, Headers requestHeaders) {
        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("fetchStocksV1", "fetchStocksV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();
        List<Stock> filteredList;
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
            if (s != null) {
                if (updateStockServiceSac.updateStockService(stockMapper)) {
                    return new StockResponse(true);
                }
            }
            return new StockResponse(false);
        }
    catch(Exception e)
    {
        throw new RuntimeException(e);
    }
        finally{
        }
    }
}
