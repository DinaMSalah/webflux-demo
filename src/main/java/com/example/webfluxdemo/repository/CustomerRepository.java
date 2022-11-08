package com.example.webfluxdemo.repository;


import com.example.webfluxdemo.dto.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer>{

}
