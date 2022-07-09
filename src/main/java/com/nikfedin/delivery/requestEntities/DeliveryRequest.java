package com.nikfedin.delivery.requestEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryRequest {
    @JsonProperty
    private UserRequest user;
    @JsonProperty
    private String timeslotId;
}
