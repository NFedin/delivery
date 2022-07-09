package com.nikfedin.delivery.controllers;

import com.nikfedin.delivery.models.Address;
import com.nikfedin.delivery.models.Timeslot;
import com.nikfedin.delivery.services.TimeslotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
@Slf4j
public class TimeslotController {
    TimeslotService timeslotService;

    @PostMapping(path = "/initializeTimeslots")
    public ResponseEntity<String> initializeTimeslots() throws FileNotFoundException, ParseException {
        timeslotService.initializeTimeslots();
        return ResponseEntity.status(HttpStatus.OK).body("Timeslots initialized");
    }

    @PostMapping(path = "/timeslots")
    public ResponseEntity<List<Timeslot>> retrieveTimeslots(@RequestBody Address address) {
        String postcode = address.getAddressId().getPostcode();
        return ResponseEntity.status(HttpStatus.OK).body(timeslotService.getAllAvailableTimeslots(postcode));
    }
}
