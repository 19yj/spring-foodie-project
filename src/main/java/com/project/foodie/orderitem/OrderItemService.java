package com.project.foodie.orderitem;

import com.project.foodie.menu.Menu;
import com.project.foodie.menu.MenuRepository;
import com.project.foodie.orders.Order;
import com.project.foodie.orders.OrderRepository;
import com.project.foodie.user.User;
import com.project.foodie.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuRepository menuRepository;

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public Optional<OrderItem> findOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    public OrderItem addOrderItemForUser(OrderItemRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // find or create pending order
        Order order = orderRepository.findByUser_IdAndStatus(user.getId(), "pending")
                .orElseGet(() -> {
                    Order newOrder = new Order();
                    newOrder.setUser(user);
                    newOrder.setStatus("pending");
                    newOrder.setOrderDate(LocalDateTime.now());
                    return orderRepository.save(newOrder);
                });

        return createAndSaveOrderItem(order, request);
    }

    // for guest users
    public OrderItem addOrderItemForGuest(OrderItemRequest request) {
        String guestSessionId = request.getGuestSessionId();

        if (guestSessionId == null || guestSessionId.isEmpty()) {
            throw new RuntimeException("Guest session Id not found");
        }

        // find or create guest order
        Order order = orderRepository.findByGuestSessionIdAndStatus(guestSessionId, "pending")
                .orElseGet(() -> {
                    Order newOrder = new Order();
                    newOrder.setGuestSessionId(guestSessionId);
                    newOrder.setStatus("pending");
                    newOrder.setOrderDate(LocalDateTime.now());
                    return orderRepository.save(newOrder);
                });

       return createAndSaveOrderItem(order, request);
    }

    private OrderItem createAndSaveOrderItem(Order order, OrderItemRequest request) {
        Menu menu = menuRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMenu(menu);
        orderItem.setItemId(String.valueOf(menu.getId()));
        orderItem.setItemName(menu.getItemName());
        orderItem.setPrice(menu.getPrice());
        orderItem.setQuantity(request.getQuantity());
        orderItem.setTotalPrice(menu.getPrice() * request.getQuantity());

        return orderItemRepository.save(orderItem);
    }

}
