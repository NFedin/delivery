package com.nikfedin.delivery.repositories;

import com.nikfedin.delivery.models.Delivery;
import com.nikfedin.delivery.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findAllByTimeslotIn(List<Timeslot> timeslots);
}
