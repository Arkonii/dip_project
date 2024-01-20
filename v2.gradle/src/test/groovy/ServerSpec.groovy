import groovy.json.JsonSlurper
import spock.lang.*

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ServerSpec extends Specification {

    @Shared def server
    def setupSpec(){
        and:"uruchomienie serwera przed testowaniem"
        server = new Server()
        server.startServer()
    }
    def cleanupSpec(){
        and:"zamknięcie serwera po testowaniu"
        server.stopServer()
    }



    def "Sprawdzenie poprawności zwracania kodu statusu"(){
        given:"utworzenie żadania GET"
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1080/example"))
                .GET()
                .build();

        when:"wysłanie żądania GET"
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        then:"sprawdzenie kodu statusu"
        httpResponse.statusCode()==200
    }

    def "Sprawdzenie poprawności działania kodu 404(nie znaleziono)"(){
        given:"utworzenie żadania GET"
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1080/"))
                .GET()
                .build();

        HttpClient httpClient2 = HttpClient.newHttpClient();
        HttpRequest httpRequest2 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1080/"))
                .GET()
                .build();

        when:"wysłanie żądania GET"
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> httpResponse2 = httpClient2.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
        then:"sprawdzenie kodu statusu"
        httpResponse.statusCode()==404
        httpResponse2.statusCode()==404

    }
    def "Sprawdzenie poprawności otrzymywanych danych"(){
        given:"utworzenie żadania GET"
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1080/example"))
                .GET()
                .build();

        when:"wysłanie żądania GET"
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        then:"sprawdzenie czy zwrocony obiekt jest obiektem json"
        and:"sprawdzenie czy zwrócono kod 200"
        assert httpResponse.statusCode()==200
        and:"sprawdzenie czy zwrócony obiekt nie jest null"
        assert httpResponse.body()!=null
        new JsonSlurper().parseText(httpResponse.body().toString())

    }
}
