package com.implementation.portfolioValuation.dataprovider.service;


import com.google.gson.Gson;
import com.google.protobuf.ListValue;
import com.google.protobuf.util.JsonFormat;
import com.implementation.portfolioValuation.dataprovider.datamodel.Security;
import com.implementation.portfolioValuation.dataprovider.pricer.GBStockPriceGenerator;
import com.implementation.portfolioValuation.dataprovider.service.helper.SecurityServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class MarketDataProviderService
{

    private static final String stock = "STOCK";

    private final Integer deltaInSeconds;

    static ArrayBlockingQueue<String> priceQueue = new ArrayBlockingQueue<>(10);

    public MarketDataProviderService(@Value("${app.delta}") Integer delta) throws Exception
    {
        this.deltaInSeconds = delta;

        List<Security> securitiesOfInterestList = SecurityServiceQuery.getAllSecurity()
                .stream().filter(x -> x.getSecurityType().equals(stock)).collect(Collectors.toList());

        ArrayList<Callable<Double>> callables = new ArrayList<>(securitiesOfInterestList.size());
        securitiesOfInterestList.stream().forEach(x ->
        {
            Callable<Double> callable = () -> {
                try {
                    while(true) {
                        x.setPrice(GBStockPriceGenerator.generatePrice(x, delta));
                        TimeUnit.SECONDS.sleep(deltaInSeconds);
                        priceQueue.add(x.toJson());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 0.0;
            };
            callables.add(callable);
        }
        );

        ExecutorService execService = new ThreadPoolExecutor(callables.size(),callables.size(),Long.MAX_VALUE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(callables.size()));
        callables.stream().forEach(x -> execService.submit(x));
    }

    ArrayBlockingQueue<Security> stockPriceList;

    public static Security getSecurityPrice()
    {
        return gson.fromJson(priceQueue.poll(), Security.class);
    }

    static Gson gson = new Gson();
    public static Security getSecurityPrice( long timeout, TimeUnit unit) throws InterruptedException
    {
        String data = priceQueue.poll(timeout, unit);
        return gson.fromJson(data, Security.class) ;
    }

    public static Boolean hasPrice()
    {
        return priceQueue.size() > 0;
    }
}
