package service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientService {
    private static final String PROJECT_ENDPOINT = "/projects";
    private static final String TESTRUN_ENDPOINT = "%s/projects/%s/runs";
    //private static final String PROJECT_ENDPOINT = "http://192.168.56.1:8081/api/projects";
    //private static final String TESTRUN_ENDPOINT = "http://192.168.56.1:8081/api/projects/%s/runs";

    public String sendRequest(String json, String server) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + PROJECT_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response.body();
    }

    public void sendRunToProject(String json, String server, String id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(TESTRUN_ENDPOINT, server, id)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
