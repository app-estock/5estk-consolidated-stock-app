package com.market.stock.service;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockResponse;
import com.market.stock.common.AppConstants;
import com.market.stock.common.CommonUtility;
import com.market.stock.common.faulthandler.FaultCode;
import com.market.stock.common.headers.Headers;
import com.market.stock.common.logging.TransactionLog;
import com.market.stock.dao.StockRepository;
import com.market.stock.dao.entities.Stock;
import com.market.stock.exception.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeleteStocksServiceImpl implements  DeleteStocksService{


    @Autowired
    private  StockRepository repository;
    Map<String, String> extendedProperties = new HashMap<>();
    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(DeleteStocksServiceImpl.class);
    private TransactionLog transactionLog;
    @Override
    public StockResponse processRequest(String companycode, Headers requestHeaders) throws NoDataFoundException {
        StockResponse response = new StockResponse();
        estkErrorList= new ArrayList<>();
        transactionLog = new TransactionLog("StocksV1", "deleteStocksV1", "Service");
        int recordsDeleted=0;

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
        try { List<Stock> recordDeletedCount=  repository.deleteByCompanyCode(companycode);
              recordsDeleted=recordDeletedCount.size();
            if(recordsDeleted>0) {
                response.setSuccessIndicator(true);

                extendedProperties.put("recordDeleted:",recordsDeleted+"records");
            }
            else {
                CommonUtility.addtoEstkErrorlist(estkErrorList, CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_NO_DATA_RESPONSE_TEXT.replace("#COMPANYCODE#",companycode)));
            }
            if (!CommonUtility.isListEmpty(estkErrorList)) {

                response = mapResponseFaults();
            }
        } catch (Exception e) {
            throw new NoDataFoundException(e.toString());
        }
        finally {
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
        }
        return response;
    }

    private StockResponse mapResponseFaults() {
        StockResponse response = new StockResponse();
        response.setErrorMessages(estkErrorList);
        response.setSuccessIndicator(false);
        return response;
    }
}