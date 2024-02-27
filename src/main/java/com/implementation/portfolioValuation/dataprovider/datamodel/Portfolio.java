package com.implementation.portfolioValuation.dataprovider.datamodel;

import java.util.ArrayList;

public class Portfolio
{
    public ArrayList<Position> getPortfolioPositions() {
        return portfolioPositions;
    }
    ArrayList<Position> portfolioPositions = new ArrayList<>();

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioPositions=" + portfolioPositions +
                '}';
    }
}
