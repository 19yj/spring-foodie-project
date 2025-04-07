package com.project.foodie.orders;

import com.project.foodie.orderitem.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.project.foodie.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String orderNumber;
    private String tableNo;
    private double totalAmount;
    private String paymentMethod;
    private String status; // "pending", "confirmed", "delivered"
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}
}
