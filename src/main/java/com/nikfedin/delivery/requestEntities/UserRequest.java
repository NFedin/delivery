package com.nikfedin.delivery.requestEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nikfedin.delivery.models.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    @JsonProperty
    private String username;
    @JsonProperty
    private Address address;
}
