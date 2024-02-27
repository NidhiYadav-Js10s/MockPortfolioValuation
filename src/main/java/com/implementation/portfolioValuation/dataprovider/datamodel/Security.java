package com.implementation.portfolioValuation.dataprovider.datamodel;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@DataAmount
@AllArgsConstructor
@NoArgsConstructor
@Table

public class Security
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getMaturity() {
        return maturity;
    }

    public void setMaturity(Double maturity) {
        this.maturity = maturity;
    }

    @Id
    @Column
    private int id;
    //defining name as column name
    @Column
    private String ticker;
    //defining age as column name
    @Column
    private Double maturity=0.0;

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    @Column
    private Double strikePrice=0.0;

    @Column
    private String  securityType;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column
    private Double price;

    @Override
    public String toString() {
        return "Security{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", maturity='" + maturity + '\'' +
                ", strikePrice=" + strikePrice +
                ", securityType='" + securityType + '\'' +
                ", startingPrice=" + price +
                '}';
    }

    private Double sigma;

    public void setSigma(Double sigma) {
        this.sigma = sigma;
    }

    public void setMu(Double mu) {
        this.mu = mu;
    }

    private Double mu;

    public Double getMu()
    {
        return this.mu;
    }

    public Double getSigma()
    {
        return this.sigma;
    }

    public String toJson()
    {
        String maturityData = (maturity  == null ? "0.0 " : maturity.toString());
        String strikePriceData = (strikePrice  == null ? "0.0 " : strikePrice.toString());
        String jsonValue = "{" +
                "\"id\":" + id +
                ", \"ticker\":\"" + ticker + '\"' +
                ", \"maturity\":" + maturityData +
                ", \"strikePrice\":" + strikePriceData  +
                ", \"securityType\":\"" + securityType + '\"' +
                ", \"price\":" + price +
                ", \"sigma\":" + sigma +
                ", \"mu\":" + mu +
                '}';
        return jsonValue;
    }
}