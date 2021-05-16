package service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientService {
    private static final String PROJECT_ENDPOINT = "%s/projects";
    private static final String TESTRUN_ENDPOINT = "%s/projects/%s/runs";
    //private static final String PROJECT_ENDPOINT = "http://192.168.56.1:8081/api/projects";
    //private static final String TESTRUN_ENDPOINT = "http://192.168.56.1:8081/api/projects/%s/runs";

    public String postProjectRequest(String json, String server) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(PROJECT_ENDPOINT, server)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = this.getHttpResponse(request);
        return response.body();
    }

    public void postRunToProjectRequest(String json, String server, String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(TESTRUN_ENDPOINT, server, id)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = this.getHttpResponse(request);
    }

    private HttpResponse<String> getHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }
}
