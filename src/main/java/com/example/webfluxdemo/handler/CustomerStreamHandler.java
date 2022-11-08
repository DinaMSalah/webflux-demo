package com.example.webfluxdemo.handler;

import com.example.webfluxdemo.repository.CustomerDAO;
import com.example.webfluxdemo.dto.Customer;
import com.example.webfluxdemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class CustomerStreamHandler {

    private final CustomerDAO customerDAO;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerStreamHandler(CustomerRepository customerRepository,CustomerDAO customerDAO){

        this.customerRepository= customerRepository;
        this.customerDAO =customerDAO;
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customersStream = customerDAO.getCustomersStream();
        return ServerResponse.ok().
                contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersStream, Customer.class);
    }

    public Mono<ServerResponse> getCustomersFromDB(ServerRequest request) {
        Flux<Customer> customersStream = customerRepository.findAll()
                .delayElements(Duration.ofSeconds(1));

        return ServerResponse.ok().
                contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersStream, Customer.class);
    }

}
