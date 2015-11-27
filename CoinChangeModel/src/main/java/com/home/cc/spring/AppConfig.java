package com.home.cc.spring;

import com.home.cc.AmountToCanonicalEqGenerator;
import com.home.cc.currency.CurrencyBuilder;
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
    Collection<CurrencyBuilder> currencyMapperList;

    @Bean
    public AmountToCanonicalEqGenerator amountToCanonicalEqGenerator() {
        AmountToCanonicalEqGenerator amountToCanonicalEqGenerator = AmountToCanonicalEqGenerator.getInstance();
        amountToCanonicalEqGenerator.setCurrencyBuilders(currencyMapperList);
        return amountToCanonicalEqGenerator;
    }
}
