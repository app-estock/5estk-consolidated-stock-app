package com.market.stock.service;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockResponse;
import com.market.stock.common.AppConstants;
import com.market.stock.common.CommonUtility;
import com.market.stock.common.faulthandler.FaultCode;
import com.market.stock.dao.DeleteStockDao;
import com.market.stock.exception.NoDataFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeleteStocksServiceImpl implements  DeleteStocksService{

    @Autowired
    private DeleteStockDao deleteStockDao;

    private List<ErrorMessage> estkErrorList;
    @Override
    public StockResponse processRequest(String companycode) throws NoDataFoundException {
        StockResponse response = new StockResponse();
        estkErrorList= new ArrayList<>();
        Long recordsDeleted=0L;
        try {  recordsDeleted=deleteStockDao.deleteStocks(companycode);
            if(recordsDeleted>0) {
                response.setSuccessIndicator(true);
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
        return response;
    }

    private StockResponse mapResponseFaults() {
        StockResponse response = new StockResponse();
        response.setErrorMessages(estkErrorList);
        response.setSuccessIndicator(false);
        return response;
    }
}