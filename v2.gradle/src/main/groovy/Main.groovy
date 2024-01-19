import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Main {
  static void main(String[] args) {
    HttpClient httpClient = HttpClient.newHttpClient();


    HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:1080/example"))
            .GET()
            .build();

    try {
      HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      System.out.println("Status: " + httpResponse.statusCode());
      System.out.println("Treść odpowiedzi: " + httpResponse.body());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
