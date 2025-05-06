package com.project.foodie.cart;

import com.project.foodie.orderitem.OrderItem;
import com.project.foodie.orderitem.OrderItemService;
import com.project.foodie.security.JWTUtils;
import com.project.foodie.user.User;
import com.project.foodie.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private JWTUtils jwtUtil;
    @Autowired
    private UserRepository userRepository;

    private Long getUserIdFromToken(HttpServletRequest request) {
        // extract username from JWT token
        String username = jwtUtil.extractUsernameFromRequest(request);

        // fetch user using the extracted username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return user.getId();
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderItem>> getUserCart(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        return ResponseEntity.ok(orderItemService.getOrderItemsByUserId(userId));
    }

    @GetMapping("/guest")
    public ResponseEntity<List<OrderItem>> getGuestCart(
            @RequestParam String guestSessionId) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByGuestSessionId(guestSessionId));
    }
}