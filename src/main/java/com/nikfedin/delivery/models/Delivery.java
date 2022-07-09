package com.nikfedin.delivery.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Delivery {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Status status;
    @ManyToOne
    private Timeslot timeslot;
}
