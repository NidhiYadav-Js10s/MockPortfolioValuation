package com.implementation.portfolioValuation.dataprovider.printer;

import com.implementation.portfolioValuation.dataprovider.datamodel.Portfolio;

public interface ISubscriber
{
    public void updatedPortfolio(Portfolio portfolio);
}
