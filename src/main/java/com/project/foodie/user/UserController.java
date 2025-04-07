package com.project.foodie.user;

import com.project.foodie.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // marks this class as a REST API controller
@RequestMapping("/api/auth") // base URL for all the methods in this controller

// @PostMapping, @GetMapping, @DeleteMapping: annotations for HTTP methods (POST, GET, DELETE)

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    // registration endpoint
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // save user to database
        User registeredUser = userService.saveUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    // login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> foundUser = userService.getUserByUsername(user.getUsername());

        // if user is found and password matches
        if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            // generate a token using their id
            String token = jwtUtils.generateToken(foundUser.get().getId());
            return ResponseEntity.ok("Bearer " + token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

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
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
