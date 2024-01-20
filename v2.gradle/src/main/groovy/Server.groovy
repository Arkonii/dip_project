import org.mockserver.integration.ClientAndServer
import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response
import groovy.json.JsonOutput

class Server {
    static List<Integer> idTab = [0, 1, 2]
    static List<String> nameTab = ["Tomasz Kowalski", "Katarzyna Ząb", "Kacper Mak"]
    static List<Integer> ageTab = [30, 28, 33]
    static ClientAndServer mockServer

    static void startServer() {
        def a = 1080
        mockServer = new ClientAndServer(a)

        def jsonMap = [
                ids: idTab,
                names: nameTab,
                ages: ageTab
        ]
        def jsonResponse = JsonOutput.toJson(jsonMap)

        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/example"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse))

        mockServer
                .when(request())
                .respond(response().withStatusCode(404).withBody("Nie znaleziono"))


        System.out.println("Obsługiwany port: $a")
    }
    static void stopServer() {
        if (mockServer != null) {
            mockServer.stop()
            System.out.println("Serwer został zamknięty.")
        }
    }


}
