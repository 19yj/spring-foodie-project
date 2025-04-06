package com.project.foodie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable long id) {
        return  orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
    }
}
