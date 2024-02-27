package com.implementation.portfolioValuation.dataprovider.service;

import com.implementation.portfolioValuation.dataprovider.datamodel.Portfolio;
import com.implementation.portfolioValuation.dataprovider.datamodel.Position;
import com.implementation.portfolioValuation.dataprovider.datamodel.Security;
import com.implementation.portfolioValuation.dataprovider.service.helper.CSVReader;
import com.implementation.portfolioValuation.dataprovider.service.helper.EuropeanOptionPricer;
import com.implementation.portfolioValuation.dataprovider.service.helper.SecurityServiceQuery;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PortfolioManager
{
    Portfolio managedPortfolio;
    List<Security> soi;
    public PortfolioManager(String filename) throws Exception
    {
        managedPortfolio = CSVReader.readCSVData(filename);
        soi = SecurityServiceQuery.getAllSecurity();
    }

    public void markToMarketPortfolio() throws Exception
    {
       Security security = MarketDataProviderService.getSecurityPrice(12, TimeUnit.SECONDS);
       if(security == null)
            return;

       List<Position> affectedPositions = managedPortfolio.getPortfolioPositions().stream()
               .filter(x -> x.getSymbol().contains(security.getTicker())).collect(Collectors.toList());

       affectedPositions.stream().forEach( x -> {
               Security identifiedMatch = soi.stream().filter( sec-> sec.getTicker().equals(x.getSymbol()))
                       .findFirst().orElse(null);
               if(identifiedMatch.getSecurityType().equals("STOCK"))
               {
                   x.setMarketValue(security.getPrice()*x.getUnits());
                   x.setPrice(security.getPrice());
               }
               else
               {
                   EuropeanOptionPricer.OptionType ot = EuropeanOptionPricer.OptionType.valueOf(identifiedMatch.getSecurityType());
                   Double effectivePrice = EuropeanOptionPricer.calculatePrice(ot, security.getPrice(), identifiedMatch.getStrikePrice(),
                           identifiedMatch.getMaturity(), security.getSigma());
                   x.setMarketValue(effectivePrice*x.getUnits());
                   x.setPrice(effectivePrice);
               }
       });

        System.out.println("Affected positions are :: " + affectedPositions);
        System.out.println("The entire portfolio is now :: " + managedPortfolio);
    }

}
