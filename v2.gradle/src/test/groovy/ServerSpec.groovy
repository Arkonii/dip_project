import spock.lang.*

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ServerSpec extends Specification {
    def "Powinien zwrocic kod 200"(){
        given:
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1080/example"))
                .GET()
                .build();
        when:
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        then:
        httpResponse.statusCode()==200

    }
}
