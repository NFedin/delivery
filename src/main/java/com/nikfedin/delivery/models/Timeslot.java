package com.nikfedin.delivery.models;

import com.nikfedin.delivery.utils.StringListConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
public class Timeslot {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime startDate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime endDate;

    private int deliveriesCount;

    @Version
    private Long version;

    @Convert(converter = StringListConverter.class)
    private List<String> supportedPostcodes;

}
