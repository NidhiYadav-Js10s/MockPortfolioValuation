package com.implementation.portfolioValuation.dataprovider.printer;

import com.implementation.portfolioValuation.dataprovider.datamodel.Portfolio;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PrettyPrinter implements ISubscriber
{
    static int counter = 0;
    static String FORMATTER = "%,.2f";

    @Override
    public void updatedPortfolio(Portfolio portfolio)
    {
        printPortfolio(portfolio);
    }

    private void printPortfolio(Portfolio portfolio)
    {
        String[][]  data = new String[portfolio.getPortfolioPositions().size()+2][4];

        data[0][0] = "symbol";
        data[0][1] = "price";
        data[0][2] = "qty";
        data[0][3] = "value";
        AtomicInteger maxLength = new AtomicInteger(0);
        AtomicInteger finalCounter=new AtomicInteger(1);

        portfolio.getPortfolioPositions().stream().forEach(x ->  {
            data[finalCounter.get()][0] = x.getSymbol();
            if(x.getSymbol().length() > maxLength.get())
                maxLength.set(x.getSymbol().length());

            data[finalCounter.get()][1] = x.getPrice() == null ? "" : String.format(FORMATTER,x.getPrice());
            if(data[finalCounter.get()][1].length() > maxLength.get())
                maxLength.set(data[finalCounter.get()][1].length());

            data[finalCounter.get()][2] = String.format(FORMATTER,x.getUnits());
            if(data[finalCounter.get()][2] .length() > maxLength.get())
                maxLength.set(x.getUnits().toString().length());

            data[finalCounter.get()][3] = x.getMarketValue() == null ? "" : String.format(FORMATTER,x.getMarketValue());
            if(data[finalCounter.get()][3].length() > maxLength.get())
                maxLength.set(data[finalCounter.get()][3].length());

            finalCounter.getAndIncrement();
        });

        data[finalCounter.get()][0] = "#Total portfolio Value";
        data[finalCounter.get()][1] = "";
        data[finalCounter.get()][2] = "";
        data[finalCounter.getAndIncrement()][3] = String.format(FORMATTER,portfolio.getNetAssetValue());


        System.out.println("## " + counter++ + " Market Data Update");
        System.out.println(portfolio.getChangedSecurity().getTicker() + " price changed to " + portfolio.getChangedSecurity().getPrice());
        System.out.println();

        System.out.println("## Portfolio");

        maxLength.set(maxLength.get()+2);

        finalCounter.set(0);
        Arrays.stream(data).forEach(x -> {
            System.out.printf("%-"+maxLength.get()+"s%"+maxLength.get()+"s%"
                    +maxLength.get()+"s%"+maxLength.get()+"s", x[0],x[1],x[2],x[3]);
            System.out.println();
            if(finalCounter.get() == portfolio.getPortfolioPositions().size())
                System.out.println();
            finalCounter.getAndIncrement();
        });
    }
}
