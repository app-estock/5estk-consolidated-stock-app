package com.market.stock;

import com.market.stock.config.JwtFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.market.stock.dao.repository")
public class StockApplication {
	@Bean
	public FilterRegistrationBean jwtFilter(){
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilterConfig());
		registrationBean.addUrlPatterns("/api/v1.0/*");
		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

}
