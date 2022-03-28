package com.market.stock.service;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockPriceDetail;
import com.market.stock.api.StockResponse;
import com.market.stock.common.AppConstants;
import com.market.stock.common.CommonUtility;
import com.market.stock.common.headers.Headers;
import com.market.stock.common.logging.TransactionLog;
import com.market.stock.dao.entities.Stock;
import com.market.stock.dao.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;



@Service
public class FetchStocksServiceImpl implements FetchStocksService {
    @Autowired
    private  StockRepository stockRepository;

    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(FetchStocksServiceImpl.class);
    private TransactionLog transactionLog;



    @Override
    public StockResponse processRequest(String companycode, Date startDate, Date endDate, Headers requestHeaders) throws ParseException {

        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("StocksV1", "fetchStocksV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();

        //region transaction log population
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog(requestHeaders.toString());
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        //endregion
     try {
         List<Stock>listOfStock=findStockPricesforACompany(companycode,endDate,startDate);
         transactionLog.setStatus(AppConstants.SUCCESS);
         return mapStockResponse(listOfStock);
     }
     catch (Exception e)
     {
         throw new RuntimeException(e);
     }
     finally{

         logger.info(transactionLog.toString());

     }


    }

    private StockResponse mapStockResponse(List<Stock> listOfStock)
    {

        List<StockPriceDetail> stockPriceDetailList = new ArrayList<>();
        for(Stock s :listOfStock)
        {
            stockPriceDetailList.add(new StockPriceDetail(s.getCompanyCode(),s.getPrice(),s.getCreatedDate(),s.getCreatedTime()));
        }

       return  new StockResponse(stockPriceDetailList, CommonUtility.getMaxPrice(stockPriceDetailList),CommonUtility.getMinPrice(stockPriceDetailList),CommonUtility.getAvgPrice(stockPriceDetailList),null);
    }

    public List<Stock> findStockPricesforACompany(String companycode, Date endDate, Date startDate) {
        return stockRepository.findStockByCompanyCodeAndCreatedDateBetween(companycode,startDate,endDate);
    }
}
