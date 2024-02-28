package com.implementation.portfolioValuation.dataprovider;

import com.implementation.portfolioValuation.dataprovider.printer.PrettyPrinter;
import com.implementation.portfolioValuation.dataprovider.service.MarketDataProviderService;
import com.implementation.portfolioValuation.dataprovider.service.PortfolioManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class InitializationEngine {

	public static void main(String[] args) throws Exception
	{
		ApplicationContext ctxt = SpringApplication.run(InitializationEngine.class, args);
		MarketDataProviderService mdp  = ctxt.getBean("marketDataProviderService", MarketDataProviderService.class);
		PortfolioManager manager = new PortfolioManager("PortfolioFile.csv");
		PrettyPrinter printer  = ctxt.getBean("prettyPrinter", PrettyPrinter.class);
		manager.subscribe(printer);
		while (true)
		{
			manager.markToMarketPortfolio();
		}

	}

}
