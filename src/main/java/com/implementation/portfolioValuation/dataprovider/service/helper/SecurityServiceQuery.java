package com.implementation.portfolioValuation.dataprovider.service.helper;

import com.google.gson.Gson;
import com.implementation.portfolioValuation.dataprovider.datamodel.Security;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class SecurityServiceQuery {
    static HttpClient httpClient = HttpClient.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            .connectTimeout(Duration.ofSeconds(30))
                            .build();

    public static List<Security> getAllSecurity() throws Exception{

        HttpRequest httpRequest
                = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .GET()
                .uri(URI.create("http://localhost:8080/Security/getAllSecurity"))
                .build();

        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<Security> securitiesOfInterestList = List.of(fromJson(httpResponse.body().toString()));
        System.out.println(securitiesOfInterestList);
        return  securitiesOfInterestList;
    }

    public static Security[] fromJson(String json) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json,Security[].class);
    }
}
