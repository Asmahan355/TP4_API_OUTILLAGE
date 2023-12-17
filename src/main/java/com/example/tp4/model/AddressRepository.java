package com.example.tp4.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<com.example.tp4.model.Address, Long> {
}