package com.market.stock.common;

import com.market.stock.api.ErrorMessage;
import com.market.stock.api.StockPriceDetail;
import com.market.stock.common.faulthandler.FaultCode;
import com.market.stock.common.faulthandler.FaultHandler;
import com.market.stock.domain.comp.StockComparator;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtility {
    public static final String DB_FORMAT_DATETIME = "yyyy-M-d HH:mm:ss";
    public static double getMaxPrice(List<StockPriceDetail> stockPriceDetailsList)
    {
        StockPriceDetail maxPrice= Collections.max(stockPriceDetailsList,new StockComparator());
        return maxPrice.getStockPrice();
    }
    public static double getMinPrice(List<StockPriceDetail> stockPriceDetailsList)
    {
        StockPriceDetail minPrice= Collections.min(stockPriceDetailsList,new StockComparator());
        return minPrice.getStockPrice();
    }
    public static Double getAvgPrice(List<StockPriceDetail> stockPriceDetailsList)
    {
        return stockPriceDetailsList.stream().collect(Collectors.averagingDouble(a->a.getStockPrice()));
    }
    public static Date getDate(String dateStr, String format) {
        final DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }


    public static FaultHandler createFault(FaultCode faultCode, String responsefaultMessage)
    {
        return new FaultHandler(faultCode.name(), faultCode.getValue(),responsefaultMessage);
    }
    public static List<ErrorMessage> addtoEstkErrorlist(List<ErrorMessage> errorlist, FaultHandler fault){
        ErrorMessage em = new ErrorMessage();
        em.setErrorCode(fault.getFaultCode());
        em.setErrorDef(fault.getFaultMessage()+":"+fault.getResponseFaultMessage());
        errorlist.add(em);
        return errorlist;
    }
    public static <T> boolean isListEmpty(List<T> list )
    {
        if(list!=null) {
            return list.isEmpty();
        }
        return false;
    }
    public static DateTime ExtractDateTime(Timestamp datetime)
    {//2022-02-15T18:34:31.462Z"
        String dt= datetime.toString().replace(" ","D").replace(".","T");
        String[] date=dt.toString().split("D");
        String temp=date[1];
        String []time=temp.split("T");

        return new DateTime(toDate(date[0],time[0]),time[0]);
    }
    private static Date toDate(String date,String time)
    {   String datetime=date+" "+time;
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
