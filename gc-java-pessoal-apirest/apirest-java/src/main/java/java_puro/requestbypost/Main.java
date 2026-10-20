package java_puro.requestbypost;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Gson json = new Gson();
        Product product = new Product();
        product.setTitle("Pizza");
        String jsonRequest = json.toJson(product);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("https://dummyjson.com/products/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
