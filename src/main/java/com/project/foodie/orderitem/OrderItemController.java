package com.project.foodie.orderitem;

import com.project.foodie.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        String token = authHeader.replace("Bearer ", "");

        try {
            String userId = jwtUtil.extractUserId(token);

            orderItemService.addOrderItemForUser(request, userId);
            return ResponseEntity.ok("Item added to cart");
        } catch (Exception e) {
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
