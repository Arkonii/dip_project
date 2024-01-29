package com.example


import spock.lang.Specification


import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse



class ApplicationSpec extends Specification {

    static App

    def setupSpec() {
        App = new Application()
        App.main()
    }

    def cleanupSpec() {
        App.mainStop()
    }

    def "Sprawdzenie aktywności serwera"() {
        given :
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .GET()
                .build();

        when:"wysłanie żądania GET"
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        then:"sprawdzenie kodu statusu"
        httpResponse.statusCode()==200
        httpResponse.body()=="Spring Boot API jest aktywne"
    }
    def "Sprawdzenie aktywności bazy danych na serwerze"() {
        given :
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/database-status"))
                .GET()
                .build();

        when:"wysłanie żądania GET"
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        then:"sprawdzenie kodu statusu"
        httpResponse.statusCode()==200
        httpResponse.body()=="Baza danych jest aktywna"
    }
}
