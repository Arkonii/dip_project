import org.mockserver.integration.ClientAndServer
import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response

class Server {
    static void main(String[] args) {
        def a = 1080
        ClientAndServer mockServer = new ClientAndServer(a);

        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/example"))
                .respond(response()
                        .withStatusCode(200)
                        .withBody("Odpowiedz zwrotna"));

        System.out.println("Obslugiwany port : "+a);
    }
}
