package com.nikfedin.delivery.controllers;

import com.nikfedin.delivery.models.Delivery;
import com.nikfedin.delivery.requestEntities.DeliveryRequest;
import com.nikfedin.delivery.services.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
@Slf4j
public class DeliveryController {

    private DeliveryService deliveryService;

    @PostMapping(path = "/deliveries")
    public void bookDelivery(@RequestBody DeliveryRequest request) {
        deliveryService.bookDelivery(
                request.getUser().getUsername(),
                request.getUser().getAddress(),
                request.getTimeslotId());
    }

    @PostMapping(path = "/deliveries/{deliveryId}/complete")
    public void completeDelivery(@PathVariable Long deliveryId) {
        deliveryService.completeDelivery(deliveryId);
    }

    @DeleteMapping(path = "/deliveries/{deliveryId}")
    public void cancelDelivery(@PathVariable Long deliveryId) {
        deliveryService.cancelDelivery(deliveryId);
    }

    @GetMapping(path = "/deliveries/daily")
    public ResponseEntity<List<Delivery>> getDailyDeliveries() {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getDailyDeliveries());
    }

    @GetMapping(path = "/deliveries/weekly")
    public ResponseEntity<List<Delivery>> getWeeklyDeliveries() {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getWeeklyDeliveries());
    }
}
