package com.implementation.portfolioValuation.dataprovider.service.helper;

public class EuropeanOptionPricer
{

    static final Double riskFreeRate = .02;
    private static Double calculateD1(Double underlyingPrice, Double strikePrice, Double timeToMaturity, Double sigma)
    {
        Double numerator = Math.log(underlyingPrice/strikePrice) + ( riskFreeRate+ Math.pow(sigma,2)/2)*timeToMaturity;
        return numerator / (sigma*Math.sqrt(timeToMaturity));
    }

    private static Double calculateD2(Double underlyingPrice, Double strikePrice, Double timeToMaturity, Double sigma)
    {
        return calculateD1(underlyingPrice,  strikePrice, timeToMaturity,  sigma) - sigma*Math.sqrt(timeToMaturity);
    }

    public enum OptionType
    {
        CALL,
        PUT
    }
    public static Double calculatePrice(OptionType optionType, Double underlyingPrice, Double strikePrice, Double timeToMaturity, Double sigma )
    {
        Double d1Calc = calculateD1(underlyingPrice, strikePrice, timeToMaturity, sigma);
        Double d2Calc = calculateD1(underlyingPrice, strikePrice, timeToMaturity, sigma);

        Double optionPrice = 0.0d;

        switch (optionType)
        {
            case PUT -> {
                optionPrice = strikePrice*Math.exp(-1*riskFreeRate*timeToMaturity)*cumulativeProbability(-1*d2Calc)
                        - underlyingPrice*cumulativeProbability(-1*d1Calc);
                break;
            }
            case CALL -> {

                optionPrice = underlyingPrice*cumulativeProbability(d1Calc)
                        - strikePrice*Math.exp(-1*riskFreeRate*timeToMaturity)*cumulativeProbability(d2Calc);
                break;
            }
            default-> {
                throw new IllegalArgumentException("Accepted option type is only Call or Put");
            }
        }
        return Math.max(0,optionPrice);
    }

    // The Abramowitz & Stegun (1964) numerical approximation
    private static final double probability = 0.2316419;
    private static final double N1 = 0.319381530;
    private static final double N2 = -0.356563782;
    private static final double N3 = 1.781477937;
    private static final double N4 = -1.821255978;
    private static final double N5 = 1.330274429;


    private static double cumulativeProbability(double x) {

        double t = 1 / (1 + probability * Math.abs(x));
        double t1 = N1 * Math.pow(t, 1);
        double t2 = N2 * Math.pow(t, 2);
        double t3 = N3 * Math.pow(t, 3);
        double t4 = N4 * Math.pow(t, 4);
        double t5 = N5 * Math.pow(t, 5);
        double b = t1 + t2 + t3 + t4 + t5;

        double snd = Math.exp(-0.5 * Math.pow(x, 2)) / Math.sqrt(2 * Math.PI); //for testing
        double cd = 1 - (snd * b);

        double resp = 0.0;
        if( x < 0 ) {
            resp = 1 - cd;
        } else {
            resp = cd;
        }
        return resp;
    }
}

