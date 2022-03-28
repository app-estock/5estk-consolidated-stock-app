package com.market.stock.dao;
import com.market.stock.dao.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public interface StockRepository extends JpaRepository<Stock,String>
{



  List<Stock> findStockByCompanyCodeAndCreatedDateBetween(@Param("code") String code , @Param("startDate") Date startDate, @Param("endDate") Date endDate);

 List<Stock> deleteByCompanyCode(String companycode);
}
