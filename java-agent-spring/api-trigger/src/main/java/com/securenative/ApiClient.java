package com.securenative;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.logging.Logger;

public class ApiClient {
    private static final int max = 10;
    private static final int min = 1;
    private static final Logger logger = Logger.getLogger("main");

    public static Runnable trigger() {
        return ApiClient::triggerApi;
    }

    private static void triggerApi() {
        String baseUrl = "http://localhost:8080";
        if (System.getenv("BASE_URL") != null) {
            baseUrl = System.getenv("BASE_URL");
        }

        logger.info("Performing randomize event");
        String url = String.format("%s/%s", baseUrl, "randomize");
        Random random = new Random();
        int repeat = random.nextInt(ApiClient.max - ApiClient.min + 1);
        for (int i = 0; i <= repeat; i++) {
            ApiClient.sendGetRequest(url);
        }

        logger.info("Performing logout event");
        String logoutUrl = String.format("%s/%s", baseUrl, "logout");
        ApiClient.sendGetRequest(logoutUrl);

        logger.info("Performing trigger event");
        String triggerUrl = String.format("%s/%s", baseUrl, "trigger");
        ApiClient.sendGetRequest(triggerUrl);
    }

    private static void sendGetRequest(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpUriRequest getRequest = RequestBuilder.get()
                    .setUri(new URI(url))
                    .build();

            try (CloseableHttpResponse response = httpclient.execute(getRequest)) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
