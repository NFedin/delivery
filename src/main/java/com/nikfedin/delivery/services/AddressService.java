package com.nikfedin.delivery.services;

import com.nikfedin.delivery.models.Address;
import com.nikfedin.delivery.models.AddressId;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class AddressService {
    @Value("${geo.api.key}")
    private String geoApiKey;

    public Address resolveAddress(String searchTerm) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.geoapify.com/v1/geocode/search?text=" + searchTerm + "&apiKey=" + geoApiKey))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject object = new JSONObject(response.body());
        JSONObject adr = ((JSONObject) ((JSONObject) ((JSONArray) object.get("features")).get(0)).get("properties"));
        log.info("Address has been resolved");
        return new Address(new AddressId(
                adr.getString("country"),
                adr.getString("city"),
                adr.getString("postcode"),
                adr.getString("street"),
                adr.getString("housenumber")), adr.getString("address_line1"), adr.getString("address_line2"));
    }
}
