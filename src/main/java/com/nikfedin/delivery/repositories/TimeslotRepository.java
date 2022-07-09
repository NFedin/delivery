package com.nikfedin.delivery.repositories;

import com.nikfedin.delivery.models.AddressId;
import com.nikfedin.delivery.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
//    Set<Timeslot> findAllByAddresses(AddressId addressId);
//
//    List<Timeslot> findAllByStartDateLessThan(LocalDateTime dateTime);

    List<Timeslot> findAllByStartDateBetween(LocalDateTime start, LocalDateTime end);

}
