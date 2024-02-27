package com.implementation.portfolioValuation.dataprovider.pricer;

import com.implementation.portfolioValuation.dataprovider.datamodel.Security;

import java.util.Random;

public class GBStockPriceGenerator
{
    static Random random = new Random();

    public static Double generatePrice(Security stock, Integer deltaTime)
    {
        Double epsilon = random.nextDouble(0,1);
        return Math.abs(stock.getPrice() + stock.getPrice()*((stock.getMu()*deltaTime)/7257600
                + epsilon*stock.getSigma()*(Math.sqrt(deltaTime / 7257600))));
    }
}
