package com.implementation.portfolioValuation.dataprovider.pricer;

import com.implementation.portfolioValuation.dataprovider.datamodel.Security;
import java.util.Random;

public class RandomPriceGenerator
{
    public static Double generatePrice(Security stock)
    {
        Random random = new Random();
        return Math.abs(stock.getPrice() + random.nextDouble(-1,1)*stock.getPrice());
    }
}
