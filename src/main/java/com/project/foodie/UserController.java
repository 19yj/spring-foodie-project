package com.project.foodie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // marks this class as a REST API controller
@RequestMapping("/api/users") // base URL for all the methods in this controller

// @PostMapping, @GetMapping, @DeleteMapping: annotations for HTTP methods (POST, GET, DELETE)

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
                        // @RequestBody: binds the request body (user data in JSON format) to the User object
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
                                // @PathVariable: binds the id from the URL to the id parameter in the method
    public Optional<User> getUser(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
