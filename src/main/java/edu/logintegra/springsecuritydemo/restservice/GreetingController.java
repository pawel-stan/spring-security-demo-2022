package edu.logintegra.springsecuritydemo.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting prepareResponse(@RequestParam(defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), name);
    }
}
