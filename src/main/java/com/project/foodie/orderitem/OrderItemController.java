package com.project.foodie.orderitem;

import com.project.foodie.security.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private JWTUtils jwtUtil;

    @PostMapping("/create-order")
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.saveOrderItem(orderItem);
    }

    @GetMapping
    public Optional<OrderItem> getOrderItems(@PathVariable Long id) {
        return orderItemService.findOrderItemById(id);
    }

    @DeleteMapping("{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }

    // For logged-in users
    @PostMapping("/user")
    public ResponseEntity<?> addOrderItem(
            @RequestBody OrderItemRequest request,
            @RequestHeader("Authorization") String authHeader) {

        System.out.println("Full auth header: " + authHeader);
        String token = authHeader.replace("Bearer ", "");
        System.out.println("Token after stripping Bearer: " + token);

        try {
            // Add this debug block
            try {
                Claims claims = jwtUtil.extractClaims(token);
                System.out.println("Token validated successfully");
                System.out.println("Claims: " + claims);
                System.out.println("Token expires at: " + claims.getExpiration());
                System.out.println("Current time: " + new Date());
                System.out.println("Is expired: " + claims.getExpiration().before(new Date()));
            } catch (Exception e) {
                System.out.println("Token validation failed: " + e.getMessage());
                e.printStackTrace();
            }

            String username = jwtUtil.extractUsername(token);
            System.out.println("Username from token: " + username);

            orderItemService.addOrderItemForUser(request, username);
            return ResponseEntity.ok("Item added to cart");
        } catch (Exception e) {
            System.out.println("Exception in addOrderItem: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // For guests users
    @PostMapping("/guest")
    public ResponseEntity<?> addOrderItemForGuest(@RequestBody OrderItemRequest request) {
        orderItemService.addOrderItemForGuest(request);
        return ResponseEntity.ok("Item added to cart");
    }

}
