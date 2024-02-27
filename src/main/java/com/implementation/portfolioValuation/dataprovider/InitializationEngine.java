package com.implementation.portfolioValuation.dataprovider;

import com.implementation.portfolioValuation.dataprovider.service.MarketDataProviderService;
import com.implementation.portfolioValuation.dataprovider.service.PortfolioManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class InitializationEngine {

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(InitializationEngine.class, args);
		MarketDataProviderService mdp = new MarketDataProviderService(10);
		PortfolioManager manager = new PortfolioManager("PortfolioFile.csv");
		while (true) {
			//System.out.println(MarketDataProviderService.getSecurityPrice(10,TimeUnit.SECONDS));
			manager.markToMarketPortfolio();
		}

	}

}
