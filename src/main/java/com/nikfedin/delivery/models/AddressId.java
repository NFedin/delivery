package com.nikfedin.delivery.models;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class AddressId implements Serializable {

    private String country;

    private String city;

    private String postcode;

    private String street;

    private String houseNumber;
}
