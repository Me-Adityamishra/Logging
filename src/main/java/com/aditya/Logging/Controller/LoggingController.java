package com.aditya.Logging.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoggingController {
    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private Map<Integer, String> userDatabase = new HashMap<>();

    public LoggingController() {
        // Simulating a small database of users
        userDatabase.put(1, "John Doe");
        userDatabase.put(2, "Jane Smith");
        userDatabase.put(3, "Virat Kohli");
        userDatabase.put(4, "Adam Markram");
    }

    // INFO log for fetching user details
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable int id) {
        if (userDatabase.containsKey(id)) {
            logger.info("Fetching details for user with ID: {}", id);
            return "User found: " + userDatabase.get(id);
        } else {
            logger.warn("User with ID: {} not found", id);
            return "User not found";
        }
    }

    // DEBUG log for adding a user
    @PostMapping("/user")
    public String addUser(@RequestParam String name) {
        int id = userDatabase.size() + 1;
        userDatabase.put(id, name);
        logger.debug("Added user: {} with ID: {}", name, id);
        return "User added successfully: " + name;
    }

    // TRACE log for processing requests
    @GetMapping("/process")
    public String processRequest() {
        logger.trace("Starting a detailed trace of the request processing flow.");
        try {
            // Simulating some complex logic
            Thread.sleep(500);
            logger.trace("Intermediate step completed successfully.");
            Thread.sleep(500);
            logger.trace("Final step completed.");
            return "Request processed successfully.";
        } catch (InterruptedException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            return "Request processing failed.";
        }
    }

    // ERROR log for simulating a failure
    @GetMapping("/simulate-error")
    public String simulateError() {
        try {
            int result = 10 / 0; // Will throw an exception
            return "Result: " + result;
        } catch (ArithmeticException e) {
            logger.error("An error occurred: Division by zero. Details: {}", e.getMessage());
            return "Error occurred: Division by zero.";
        }
    }

    // WARN log for deprecated endpoints
    @GetMapping("/deprecated")
    public String deprecatedEndpoint() {
        logger.warn("Access to deprecated endpoint detected. Please migrate to the latest API version.");
        return "This endpoint is deprecated. Please use the latest version.";
    }
}
