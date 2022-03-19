package com.market.stock.domain.comp;

import com.market.stock.api.StockPriceDetail;

import java.util.Comparator;

public class StockComparator implements Comparator<StockPriceDetail> {

    @Override
    public int compare(StockPriceDetail o1, StockPriceDetail o2) {
        return o1.getStockPrice().compareTo(o2.getStockPrice());
    }
}
