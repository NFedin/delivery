package com.nikfedin.delivery.services;

import com.nikfedin.delivery.models.Address;
import com.nikfedin.delivery.models.Delivery;
import com.nikfedin.delivery.models.Status;
import com.nikfedin.delivery.models.Timeslot;
import com.nikfedin.delivery.repositories.DeliveryRepository;
import com.nikfedin.delivery.repositories.TimeslotRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@EnableRetry
@Service
@AllArgsConstructor
@Slf4j
public class DeliveryService {
    DeliveryRepository deliveryRepository;
    TimeslotRepository timeslotRepository;

    public void cancelDelivery(Long deliveryId) {
        Optional<Delivery> delOptional = deliveryRepository.findById(deliveryId);
        if (delOptional.isPresent()) {
            Delivery delivery = delOptional.get();
            Optional<Timeslot> timeslotOptional = timeslotRepository.findById(delivery.getTimeslot().getId());
            if (timeslotOptional.isPresent()) {
                Timeslot timeslot = timeslotOptional.get();
                timeslot.setDeliveriesCount(timeslot.getDeliveriesCount() - 1);
                timeslotRepository.saveAndFlush(timeslot);
            }
            delivery.setStatus(Status.CANCELED);
            deliveryRepository.saveAndFlush(delivery);
            log.info("Delivery was canceled");
        } else {
            log.info("Delivery was not found");
        }
    }

    public void completeDelivery(Long deliveryId) {
        Optional<Delivery> delOptional = deliveryRepository.findById(deliveryId);
        if (delOptional.isPresent()) {
            Delivery delivery = delOptional.get();
            delivery.setStatus(Status.COMPLETED);
            deliveryRepository.saveAndFlush(delivery);
            log.info("Delivery was completed");
        } else {
            log.info("Delivery was not found");
        }
    }

    public List<Delivery> getWeeklyDeliveries() {
        LocalDateTime now = LocalDateTime.of(
                LocalDate.of(2021, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                LocalTime.now()
        );

        LocalDateTime startOfWeek = LocalDateTime.of(
                now.minusDays(now.getDayOfWeek().getValue()).toLocalDate(),
                LocalTime.MIN);
        LocalDateTime endOfWeek = LocalDateTime.of(
                now.plusDays(7 % now.getDayOfWeek().getValue()).toLocalDate(),
                LocalTime.MAX);
        List<Timeslot> timeslots = timeslotRepository.findAllByStartDateBetween(startOfWeek, endOfWeek);
        return deliveryRepository.findAllByTimeslotIn(timeslots);
    }

    @Retryable
    @Transactional
    public void bookDelivery(String user, Address address, String timeslotId) {
        Optional<Timeslot> optionalTimeslot = timeslotRepository.findById(Long.parseLong(timeslotId));
        if (optionalTimeslot.isPresent()) {
            Timeslot timeslot = optionalTimeslot.get();
            String postcode = address.getAddressId().getPostcode();
            if (timeslot.getSupportedPostcodes().contains(postcode)
                    && timeslot.getDeliveriesCount() < 2
                    && getDailyDeliveries().size() < 10) {
                timeslot.setDeliveriesCount(timeslot.getDeliveriesCount() + 1);
                Delivery delivery = Delivery.builder()
                        .status(Status.SCHEDULED)
                        .timeslot(timeslot)
                        .build();
                timeslotRepository.saveAndFlush(timeslot);
                deliveryRepository.saveAndFlush(delivery);

                log.info("Delivery created successfully for user:" + user);
            } else {
                log.info("Timeslot already has 2 deliveries or does not support the address");
            }
        } else {
            log.info("Timeslot not found");
        }

    }

    public List<Delivery> getDailyDeliveries() {
        List<Timeslot> timeslots = timeslotRepository.findAllByStartDateBetween(
                LocalDateTime.of(LocalDate.of(2021, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()), LocalTime.MIN),
                LocalDateTime.of(LocalDate.of(2021, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()), LocalTime.MAX));
        return deliveryRepository.findAllByTimeslotIn(timeslots);
    }
}
