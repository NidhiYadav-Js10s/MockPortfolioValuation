package com.implementation.portfolioValuation.dataprovider.service.helper;

import com.implementation.portfolioValuation.dataprovider.datamodel.Portfolio;
import com.implementation.portfolioValuation.dataprovider.datamodel.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSVReader
{
    public static Portfolio readCSVData(String filename)
    {
        Portfolio portfolio = new Portfolio();
        BufferedReader br = new BufferedReader(new InputStreamReader(CSVReader.class.getClassLoader().getResourceAsStream(filename)));
        try{
            String line;
            int i =0;
            while((line = br.readLine()) != null)
            {
                //skip the header row
                if(i++ == 0)
                    continue;
               String[] inputElements= line.split(",");
               portfolio.getPortfolioPositions().add(new Position(inputElements[0],Integer.parseInt(inputElements[1].trim())));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return portfolio;
    }
}
