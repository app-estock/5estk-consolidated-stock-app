package com.market.stock.service;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockPriceDetail;
import com.market.stock.api.StockResponse;
import com.market.stock.common.AppConstants;
import com.market.stock.common.CommonUtility;
import com.market.stock.common.headers.Headers;
import com.market.stock.common.logging.TransactionLog;
import com.market.stock.dao.entities.Stock;
import com.market.stock.dao.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class FetchStocksServiceImpl implements FetchStocksService {
    @Autowired
    private StockRepository stockRepository;
    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(FetchStocksServiceImpl.class);
    private TransactionLog transactionLog;
    @Autowired
    private Producer producer;
    @Autowired
    public MongoTemplate mongoTemplate;

    @Override
    public StockResponse processRequest(String companycode, Date startDate, Date endDate, Headers requestHeaders) throws ParseException {

        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("fetchStocksV1", "fetchStocksV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();
        List<Stock> filteredList;
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
         List<Stock> listOfStock = stockRepository.findByCompanyCode(companycode);
         filteredList  = listOfStock.stream().filter(d -> d.getCreatedDate().after(startDate) && d.getCreatedDate().before(endDate)).collect(Collectors.toList());
         transactionLog.setStatus(AppConstants.SUCCESS);
         return mapStockResponse(filteredList);
     }
     catch (Exception e)
     {
         throw new RuntimeException(e);
     }
     finally{

         logger.info(transactionLog.toString());
         producer.sendMessage(transactionLog.toString());
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

    public List<Stock> findStockPricesforACompany(String companycode, LocalDate endDate, LocalDate startDate) {
        return mongoTemplate.find(query(where("companycode").is(companycode)
                .and("createdDate").lte(endDate)
                .and("end_date").gte(startDate)), Stock.class);
    }
}
