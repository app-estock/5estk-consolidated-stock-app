package com.market.stock.domain.validator;

import com.market.stock.common.AppConstants;

import java.util.Map;

public class StockValidator {


    public static boolean areHeadersValid(Map<String,String> requestHeader)
    {
        if(requestHeader.containsKey(AppConstants.ESTK_TRANSACTIONID) && requestHeader.containsKey(AppConstants.ESTK_SESSIONID) && requestHeader.containsKey(AppConstants.ESTK_MESSAGEID) && requestHeader.containsKey(AppConstants.ESTK_CREATIONTIMESTAMP)){
            return true;
        }
        return false;
    }
}
