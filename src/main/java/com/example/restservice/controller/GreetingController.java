package com.example.restservice.controller;

import com.example.restservice.entity.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    @Autowired
    Greeting greeting;

    AtomicLong counter = new AtomicLong();
    @GetMapping(value = "/greeting")
    public Greeting greeting(@RequestParam(value = "name") String name) {
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey I am learning spring boot from "+ name);
        return  greeting;

    }

}
