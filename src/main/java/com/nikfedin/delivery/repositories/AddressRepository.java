package com.nikfedin.delivery.repositories;

import com.nikfedin.delivery.models.Address;
import com.nikfedin.delivery.models.AddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, AddressId> {

}
