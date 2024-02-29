package com.implementation.portfolioValuation.dataprovider.service;

import com.implementation.portfolioValuation.dataprovider.datamodel.Portfolio;
import com.implementation.portfolioValuation.dataprovider.datamodel.Position;
import com.implementation.portfolioValuation.dataprovider.datamodel.Security;
import com.implementation.portfolioValuation.dataprovider.printer.ISubscriber;
import com.implementation.portfolioValuation.dataprovider.service.helper.CSVReader;
import com.implementation.portfolioValuation.dataprovider.service.helper.EuropeanOptionPricer;
import com.implementation.portfolioValuation.dataprovider.service.helper.SecurityServiceQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Lazy
public class PortfolioManager
{
    Portfolio managedPortfolio;
    List<Security> soi;
    public PortfolioManager(@Value("${app.portfolio.filename}") String filename) throws Exception
    {
        managedPortfolio = CSVReader.readCSVData(filename);
        soi = SecurityServiceQuery.getAllSecurity();
        Thread t = new Thread(()-> {
            try {
                while(true)
                 this.markToMarketPortfolio();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        t.start();

    }

    private void markToMarketPortfolio() throws Exception
    {
        this.managedPortfolio.setChangedSecurity( MarketDataProviderService.getSecurityPrice(9, TimeUnit.SECONDS));
       if(this.managedPortfolio.getChangedSecurity() == null)
            return;

       List<Position> affectedPositions = managedPortfolio.getPortfolioPositions().stream()
               .filter(x -> x.getSymbol().contains(this.managedPortfolio.getChangedSecurity().getTicker())).collect(Collectors.toList());

       affectedPositions.stream().forEach( x -> {
               Security identifiedMatch = soi.stream().filter( sec-> sec.getTicker().equals(x.getSymbol()))
                       .findFirst().orElse(null);
               if(identifiedMatch.getSecurityType().equals("STOCK"))
               {
                   x.setMarketValue(this.managedPortfolio.getChangedSecurity().getPrice()*x.getUnits());
                   x.setPrice(this.managedPortfolio.getChangedSecurity().getPrice());
               }
               else
               {
                   EuropeanOptionPricer.OptionType ot = EuropeanOptionPricer.OptionType.valueOf(identifiedMatch.getSecurityType());
                   Double effectivePrice = EuropeanOptionPricer.calculatePrice(ot, this.managedPortfolio.getChangedSecurity().getPrice(), identifiedMatch.getStrikePrice(),
                           identifiedMatch.getMaturity(), this.managedPortfolio.getChangedSecurity().getSigma());
                   x.setMarketValue(effectivePrice*x.getUnits());
                   x.setPrice(effectivePrice);
               }
       });

       this.managedPortfolio.setNetAssetValue( this.managedPortfolio.getPortfolioPositions().stream().mapToDouble(
               x -> x.getMarketValue() == null ? 0.0 : x.getMarketValue()
       ).sum());
       this.subscribers.stream().parallel().forEach(x -> x.updatedPortfolio(this.managedPortfolio));
    }

    ArrayList<ISubscriber> subscribers = new ArrayList<>();

    public void subscribe(ISubscriber subscriber)
    {
        subscribers.add(subscriber);
    }



}
