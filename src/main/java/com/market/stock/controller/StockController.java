package com.market.stock.controller;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockRequest;
import com.market.stock.api.StockResponse;
import com.market.stock.common.AppConstants;
import com.market.stock.common.CommonUtility;
import com.market.stock.common.faulthandler.FaultCode;
import com.market.stock.common.faulthandler.FaultHandler;
import com.market.stock.common.headers.Headers;
import com.market.stock.common.logging.TransactionLog;
import com.market.stock.domain.mapper.StockMapper;
import com.market.stock.domain.validator.StockValidator;
import com.market.stock.service.AddStockService;
import com.market.stock.service.DeleteStocksService;
import com.market.stock.service.FetchStocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Validated
@RestController
public class StockController {


    private TransactionLog transactionLog;
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);


    @Autowired
    private FetchStocksService fetchStockService;
    @Autowired
    private AddStockService addStockService;
    @Autowired
    private DeleteStocksService deleteStockService;

    @PostMapping(path = "/api/v1.0/market/stock/add/{companycode}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity addStockV1(@RequestHeader Map<String,String> requestHeaders,@RequestBody StockRequest request, @PathVariable("companycode") final String companyCode, Error error) {
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("StockV1", "addStocksV1", "Controller");
        StockResponse response = null;
        try{
            if(StockValidator.areHeadersValid(requestHeaders)) {
                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);
                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog(requestHeaders.toString());
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                //endregion

                response = addStockService.processRequest(new StockMapper(companyCode, request.getStockPrice()),headers, requestHeaders);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else
            {
                ErrorMessage errorMessage= new ErrorMessage();
                errorMessage.setErrorDef("Headers Mismatch");
                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return ResponseEntity.badRequest().headers(respHeaders).body(errorMessage);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path= "/api/v1.0/market/stock/get/{companycode}/{startdate}/{enddate}" ,produces={"application/json"})
    public ResponseEntity fetchStocksV1(@RequestHeader Map<String,String> requestHeaders,@PathVariable("companycode") final String companycode, @PathVariable("startdate") final String startdate, @PathVariable("enddate") final String enddate)
    {
        StockResponse response = null;
        List<ErrorMessage> estkErrorList = new ArrayList<>();
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("StockV1", "fetchStocksV1", "Controller");

        try{
           if(StockValidator.areHeadersValid(requestHeaders)) {
               headers = mapHeaders(requestHeaders);
               responseHeaders = mapHeadersToObject(requestHeaders);
               respHeaders.setAll(responseHeaders);


               //region transaction log population
               String methodName = new Object() {
               }.getClass().getEnclosingMethod().getName();
               transactionLog.setMethodName(methodName);
               transactionLog.setRequestLog(requestHeaders.toString());
               transactionLog.setErrorcode("NONE");
               transactionLog.setTransactionId(headers.getEstk_transactionID());
               transactionLog.setMessageId(headers.getEstk_messageID());
               transactionLog.setSessionId(headers.getEstk_sessionID());
               transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
               //endregion

               Date from = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);

               Date to = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
               response = fetchStockService.processRequest(companycode, from, to,headers);
               if(response!=null) {
                   transactionLog.setResponseLog(response.toString());
                   transactionLog.setStatus(AppConstants.SUCCESS);
                   logger.info(transactionLog.toString());
                   return ResponseEntity.ok().headers(respHeaders).body(response);
               }
               else{
                   StockResponse responsebody=mapNoDataFoundResponse(estkErrorList);
                   transactionLog.setErrorcode(FaultCode.ESTK0404.name());
                   transactionLog.setErrorMessageList(estkErrorList);
                   transactionLog.setResponseLog(responsebody.toString());
                   transactionLog.setStatus(AppConstants.SUCCESS);
                   logger.info(transactionLog.toString());
                   return ResponseEntity.ok().headers(respHeaders).body(responsebody);
               }
           }
           else
           {
               ErrorMessage errorMessage= new ErrorMessage();
               errorMessage.setErrorDef("Headers Mismatch");
               transactionLog.setStatus(AppConstants.FAIL);
               logger.error(transactionLog.toString());
               return ResponseEntity.badRequest().headers(respHeaders).body(errorMessage);
           }
        }
        catch (Exception e)
        {
            StockResponse responsebody= mapInternalServerException(e, estkErrorList);
            transactionLog.setResponseLog(responsebody.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            logger.error(transactionLog.toString());
            return ResponseEntity.badRequest().headers(respHeaders).body(responsebody);
        }
        finally {
            logger.info(transactionLog.toString());
        }

    }

    @DeleteMapping(value = "/api/v1.0/market/stock/delete/{companycode}", produces = {"application/json"})
    public ResponseEntity deleteCompanyV1(@RequestHeader Map<String,String> requestHeaders,@PathVariable("companycode") final String companyCode, Error error) {

        StockResponse response = null;
        List<ErrorMessage> estkErrorList = new ArrayList<>();
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("StockV1", "deleteStocksV1", "Controller");

        try{
            if(StockValidator.areHeadersValid(requestHeaders)) {
                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);
                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog(requestHeaders.toString());
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                if (companyCode != null) {
                    response = deleteStockService.processRequest(companyCode, headers);
                    String msg = "SuccessIndicator:" + response.getSuccessIndicator();
                    transactionLog.setStatus(AppConstants.SUCCESS);

                } else {
                    response = mapEmptyInvalidRequest(estkErrorList);
                    transactionLog.setStatus(AppConstants.FAIL);


                    return  ResponseEntity.badRequest().headers(respHeaders).body(response);
                }
                if (response.getSuccessIndicator().equals(Boolean.TRUE) && CommonUtility.isListEmpty(estkErrorList)) {

                    return  ResponseEntity.ok().headers(respHeaders).body(response);
                } else {
                    if (response.getErrorMessages() != null) {
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    } else {

                        return  ResponseEntity.ok().headers(respHeaders).body(response);
                    }
                }
            }
            else{
                ErrorMessage errorMessage= new ErrorMessage();
                errorMessage.setErrorDef("Headers Mismatch");
                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return ResponseEntity.badRequest().headers(respHeaders).body(errorMessage);

            }
        } catch (Exception e) {
            response = mapInternalServerException(e, estkErrorList);
            return ResponseEntity.internalServerError().headers(respHeaders).body(response);
        }

    }


    //region private Methods
    /**
     * @param e
     * @param estkErrorList
     * @return
     */
    private StockResponse mapInternalServerException(Exception e, List<ErrorMessage> estkErrorList) {
        StockResponse response = new StockResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0000, AppConstants.ESTK0000_INTERNAL_SERVER_RESPONSE_TEXT);
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        return response;
    }

    private Map<String, String> mapHeadersToObject(Map<String, String> requestHeaders) {
        Map<String, String> objectHeaders = new HashMap<>();
        if (requestHeaders.containsKey(AppConstants.ESTK_TRANSACTIONID)) {
            if (requestHeaders.get(AppConstants.ESTK_TRANSACTIONID) == null) {
                objectHeaders.put(AppConstants.ESTK_TRANSACTIONID, UUID.randomUUID().toString());
            }
            objectHeaders.put(AppConstants.ESTK_TRANSACTIONID, requestHeaders.get(AppConstants.ESTK_TRANSACTIONID));
        }
        if (requestHeaders.containsKey(AppConstants.ESTK_SESSIONID)) {
            if (requestHeaders.get(AppConstants.ESTK_SESSIONID) == null) {
                objectHeaders.put(AppConstants.ESTK_SESSIONID, UUID.randomUUID().toString());
            }
            objectHeaders.put(AppConstants.ESTK_SESSIONID, requestHeaders.get(AppConstants.ESTK_SESSIONID));
        }
        if (requestHeaders.containsKey(AppConstants.ESTK_MESSAGEID)) {
            if (requestHeaders.get(AppConstants.ESTK_MESSAGEID) == null) {
                objectHeaders.put(AppConstants.ESTK_MESSAGEID, UUID.randomUUID().toString());
            }
            objectHeaders.put(AppConstants.ESTK_MESSAGEID, requestHeaders.get(AppConstants.ESTK_MESSAGEID));
        }
        if (requestHeaders.containsKey(AppConstants.ESTK_CREATIONTIMESTAMP)) {
            if (requestHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP) == null) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                objectHeaders.put(AppConstants.ESTK_CREATIONTIMESTAMP, timestamp.toString());
            }
            objectHeaders.put(AppConstants.ESTK_CREATIONTIMESTAMP, requestHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP));
        }
        return objectHeaders;
    }

    /**
     * @param objectHeaders- String Key value Map
     * @return Headers
     */
    private Headers mapHeaders(Map<String, String> objectHeaders) {
        Headers headers = new Headers();
        if (objectHeaders.containsKey(AppConstants.ESTK_TRANSACTIONID)) {
            if (objectHeaders.get(AppConstants.ESTK_TRANSACTIONID) == null) {
                headers.setEstk_transactionID(UUID.randomUUID().toString());
            }
            headers.setEstk_transactionID(objectHeaders.get(AppConstants.ESTK_TRANSACTIONID));
        }
        if (objectHeaders.containsKey(AppConstants.ESTK_SESSIONID)) {
            if (objectHeaders.get(AppConstants.ESTK_SESSIONID) == null) {
                headers.setEstk_sessionID(UUID.randomUUID().toString());
            }
            headers.setEstk_sessionID(objectHeaders.get(AppConstants.ESTK_SESSIONID));
        }
        if (objectHeaders.containsKey(AppConstants.ESTK_MESSAGEID)) {
            if (objectHeaders.get(AppConstants.ESTK_MESSAGEID) == null) {
                headers.setEstk_messageID(UUID.randomUUID().toString());
            }
            headers.setEstk_messageID(objectHeaders.get(AppConstants.ESTK_MESSAGEID));
        }
        if (objectHeaders.containsKey(AppConstants.ESTK_CREATIONTIMESTAMP)) {
            if (objectHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP) == null) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                headers.setEstk_creationtimestamp(timestamp.toString());
            }
            headers.setEstk_creationtimestamp(objectHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP));
        }
        return headers;
    }

    private StockResponse mapNoDataFoundResponse(List<ErrorMessage> estkErrorList){
        StockResponse response = new StockResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_NO_DATA_RESPONSE_TEXT);
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        return response;
    }

    private StockResponse mapEmptyInvalidRequest(List<ErrorMessage> estkErrorList) {
        StockResponse response = new StockResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0001, AppConstants.ESTK0001_INVALID_EMPTY_REQUEST_RESPONSE_TEXT);
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        response.setSuccessIndicator(false);
        logger.error(faultHandler.toString());
        return response;
    }
    //endRegion
}
