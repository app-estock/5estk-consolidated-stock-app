package com.market.stock.dao;

import com.market.stock.dao.entities.Stock;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DeleteStockDaoImpl implements DeleteStockDao {
    @Override
    public Long deleteStocks(String companycode) {
        Stock stock= new Stock(companycode);

        MongoTemplate mongoTemplate=new MongoTemplate(MongoClients.create(),"stockmarket");
        DeleteResult ds= mongoTemplate.remove(new Query(Criteria.where("companycode").is(stock.getCompanyCode())), Stock.class);
        System.out.println(ds.getDeletedCount());
        return ds.getDeletedCount();
    }
}
