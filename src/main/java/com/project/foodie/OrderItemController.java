package com.project.foodie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public Optional<OrderItem> getOrderItems(@PathVariable Long id) {
        return orderItemService.findOrderItemById(id);
    }

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.saveOrderItem(orderItem);
    }

    @DeleteMapping("{id}")
    public void deleteOrderItem(@PathVariable long id) {
        orderItemService.deleteOrderItem(id);
    }

}
