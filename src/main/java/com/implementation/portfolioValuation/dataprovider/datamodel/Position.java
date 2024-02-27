package com.implementation.portfolioValuation.dataprovider.datamodel;

public class Position
{
    private String symbol;
    private Integer units;
    private Double marketValue;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private Double price;
    public String getSymbol() {
        return symbol;
    }
    public Integer getUnits() {
        return units;
    }
    public Double getMarketValue() {
        return marketValue;
    }
    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }
    public Position(String sym, Integer units)
    {
        symbol = sym;
        this.units = units;
    }

    @Override
    public String toString() {
        return "Position{" +
                "symbol='" + symbol + '\'' +
                ", units=" + units +
                ", marketValue=" + marketValue +
                ", price=" + price +
                '}';
    }
}
