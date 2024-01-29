package com.example

import spock.lang.Specification
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * Klasa testów Spock, sprawdzająca poprawność uruchomienia i komunikacji z serwerer API Spring Boot
 */
class ApplicationSpec extends Specification {

    static App

    def setupSpec() {
        and:"Inicjalizacja aplikacji Spring Boot przed 1 testem"
        App = new Application()
        App.main()
    }

    def cleanupSpec() {
        and:"Zamykanie serwera po zakończeniu ostatniego testu"
        App.mainStop()
    }

    def "Sprawdzenie aktywności serwera"() {
        given :"Tworzenie klienta HTTP"
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
        given :"Tworzenie klienta HTTP"
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
