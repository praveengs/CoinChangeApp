package com.home.cc.spring;

import com.home.cc.currency.CurrencyBuilder;
import com.home.cc.currency.model.CurrencyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Collection;

/**
 * Created by prave_000 on 26/11/2015.
 */
@Configuration
@ImportResource("classpath:currency-formats.xml")
public class AppConfig {


    @Autowired
    Collection<CurrencyModel> currencyModels;

    @Bean
    public CurrencyBuilder currencyBuilder() {
        CurrencyBuilder currencyBuilder = CurrencyBuilder.getInstance();
        currencyBuilder.setCurrencyModels(currencyModels);
        return currencyBuilder;
    }
}
