package java_puro.requestbyget;

import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {


        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://pokeapi.co/api/v2/pokemon/ditto"))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse <String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
