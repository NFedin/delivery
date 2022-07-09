package com.nikfedin.delivery.repositories;

import com.nikfedin.delivery.models.Delivery;
import com.nikfedin.delivery.models.Status;
import com.nikfedin.delivery.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
//    List<Delivery> findByStatus(Status stat);
//
//    List<Delivery> findAllByStatusNot(Status status);
//
//    List<Delivery> findAllByStartDateLessThan(LocalDateTime today);
//
//    List<Delivery> findAllByStartDateBetween(LocalDateTime start, LocalDateTime end);

    List<Delivery> findAllByTimeslotIn(List<Timeslot> timeslots);
}
