package com.implementation.portfolioValuation.dataprovider.datamodel;

import java.util.ArrayList;

public class Portfolio
{
    public ArrayList<Position> getPortfolioPositions() {
        return portfolioPositions;
    }
    ArrayList<Position> portfolioPositions = new ArrayList<>();

    private Double netAssetValue;

    public Double getNetAssetValue() {
        return netAssetValue;
    }

    public void setNetAssetValue(Double netAssetValue) {
        this.netAssetValue = netAssetValue;
    }

    public Security getChangedSecurity() {
        return changedSecurity;
    }

    public void setChangedSecurity(Security changedSecurity) {
        this.changedSecurity = changedSecurity;
    }

    private Security changedSecurity;

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioPositions=" + portfolioPositions +
                ", netAssetValue=" + netAssetValue +
                ", changedSecurity=" + changedSecurity +
                '}';
    }
}
