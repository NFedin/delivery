package com.nikfedin.delivery.controllers;

import com.nikfedin.delivery.models.Address;
import com.nikfedin.delivery.services.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
@Slf4j
public class AddressController {
    AddressService addressService;

    @PostMapping(path = "/resolve-address")
    public ResponseEntity<Address> resolveAddress(@RequestBody Map<String, String> str) throws IOException, InterruptedException {
        String searchTerm = str.get("searchTerm");
        searchTerm = searchTerm.replace(" ", "%20");
        searchTerm = searchTerm.replace(",", "%2C");

        return ResponseEntity.status(HttpStatus.OK).body(addressService.resolveAddress(searchTerm));
    }
}
