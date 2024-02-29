# MockPortfolioValuation

Solution is built using Java 17. 

---

![Screenshot 2024-03-01 at 6 55 23 AM](https://github.com/NidhiYadav-Js10s/MockPortfolioValuation/assets/12932116/181b576d-dc8b-4feb-b403-452c62c36d83)



There are 2 major components of the portfolio valuation system:

1) Market Data Service  - Updates the price of the stock 
2) Portfolio Manager - Receives the update of the security price, marks to market the stock holding as well as the other holdings 
which have security as the underlier. 
3) InitializationEngine (Main Spring Application) - Orchestrates to bring the beans to life


## Market Data Service 

1) Retrieves all the securities from the security controller which is an api endpoint exposed to retrieve data from H2 database
2) Filters the securities which are stock 
3) using Geometric Brownian motion price of the stock is identified. Publishing is done by one thread per stock.
   So if you have 5 stocks - 5 threads would be generating the price and publishing it to the queue
4) each stock is published one to a array blocking queue - which is a thread safe blocking data structure
5) Price is generated every delta seconds which is configurable using application.properties file

## Portfolio Manager 

1) Portfolio Manager has a list of positions read from a file - PortfolioFile.csv. 
   Filename is configurable using application.properties file.
2) Portfolio manager has a thread which running which polls the array blocking queue exposed by market data service. 
3) If the security is received - all matching positions are found. i.e. Stocks as well as Options
4) Stock positions are marked to market using the updated price. 
5) For Options - Option price is calculated by the European Option Pricer class. Subsequently,
   options is marked to market using updated price.
6) Net Portfolio Value is calculated as sum of all market value of all the positions

## Initialization Engine 

1) H2 Database / Security Service and Controller are Components are initialized by spring boot framework
2) Market data service is loaded lazily to allow security service to startup before it
3) Portfolio manager is loaded lazily
4) PrettyPrinter subscribes to the Portfolio manager for any portfolio updates. It uses Observer Design Pattern to update the positions. 
5) Every time portfolio is updated due to security price update - Pretty printers prints the portfolio.
6) Pretty printer is a subscriber to the updates. Portfolio Manager supports publishing data to multiple subscribers if required. 

## Test Cases Covered

Test Case 1: For a price update of a security no matching position is found. 

Test Case 2: For a price update only a stock is found no Option.

Test Case 3: For a price update all the positions (options or stocks) are marked to market. 

Test Case 4: Minimum price generated is 0

Test Case 5: When there are no prices available at start of the day - valuation should be 0

