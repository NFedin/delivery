package com.nikfedin.delivery.repositories;

import com.nikfedin.delivery.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

    List<Timeslot> findAllByStartDateBetween(LocalDateTime start, LocalDateTime end);

}
