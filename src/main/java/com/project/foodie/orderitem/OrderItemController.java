package com.project.foodie.orderitem;

import com.project.foodie.security.JWTUtils;
import com.project.foodie.user.User;
import com.project.foodie.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private JWTUtils jwtUtil;
    @Autowired
    private UserRepository userRepository;

    // add item to cart (logged-in users)
//    @PostMapping("/user")
//    public ResponseEntity<?> addOrderItemForUser(
//            @RequestBody OrderItemRequest request,
//            @RequestHeader("Authorization") String authHeader) {
//
//        System.out.println("Full auth header: " + authHeader);
//        String token = authHeader.replace("Bearer ", "");
//        System.out.println("Token after stripping Bearer: " + token);
//
//        try {
//            try {
//                Claims claims = jwtUtil.extractClaims(token);
//                System.out.println("Token validated successfully");
//                System.out.println("Claims: " + claims);
//                System.out.println("Token expires at: " + claims.getExpiration());
//                System.out.println("Current time: " + new Date());
//                System.out.println("Is expired: " + claims.getExpiration().before(new Date()));
//            } catch (Exception e) {
//                System.out.println("Token validation failed: " + e.getMessage());
//                e.printStackTrace();
//            }
//
//            String username = jwtUtil.extractUsername(token);
//            System.out.println("Username from token: " + username);
//
//            orderItemService.addOrderItemForUser(request, username);
//            return ResponseEntity.ok("Item added to cart");
//
//        } catch (Exception e) {
//            System.out.println("Exception in addOrderItem: " + e.getMessage());
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
//    }

    // helper method to get user id from JWT token
    private Long getUserIdFromToken(HttpServletRequest request) {
        // extract username from JWT token
        String username = jwtUtil.extractUsernameFromRequest(request);

        // fetch user using the extracted username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return user.getId();
    }

    @PostMapping("/user")
    public ResponseEntity<?> addOrderItemForUser(
            @RequestBody OrderItemRequest request,
            HttpServletRequest httpRequest) {

        try {
            Long userId = getUserIdFromToken(httpRequest);
            orderItemService.addOrderItemForUser(request, userId);
            return ResponseEntity.ok("Item added to cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
        }
    }

    // add item to cart (guests users)
    @PostMapping("/guest")
    public ResponseEntity<?> addOrderItemForGuest(@RequestBody OrderItemRequest request) {
        orderItemService.addOrderItemForGuest(request);
        return ResponseEntity.ok("Item added to cart");
    }

    @GetMapping("/user")
    //@GetMapping("/api/order-item/user")
    public ResponseEntity<List<OrderItem>> getOrderItemForUser(HttpServletRequest httpRequest) {
        Long userId = getUserIdFromToken(httpRequest);
        List<OrderItem> cartItems = orderItemService.getOrderItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/guest")
    //@GetMapping("/api/order-item/guest")
    public ResponseEntity<List<OrderItem>> getOrderItemsForGuest(
            @RequestParam String guestSessionId  // sass guestSessionId as a query parameter
    ) {
        if (guestSessionId == null || guestSessionId.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<OrderItem> guestCartItems = orderItemService.getOrderItemsByGuestSessionId(guestSessionId);
        return ResponseEntity.ok(guestCartItems);
    }



    // after that only do
//    @DeleteMapping("/user/{orderItemId}")
//    public ResponseEntity<?> removeOrderItemForUser(
//            @PathVariable Long orderItemId,
//            HttpServletRequest httpRequest) {
//        try {
//            // Get user ID from JWT token
//            Long userId = getUserIdFromToken(httpRequest);
//
//            // Call service to remove order item for user using the user ID
//            orderItemService.removeOrderItemForUser(orderItemId, userId);
//            return ResponseEntity.ok("Item removed from cart");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
//        }
//    }

}

























