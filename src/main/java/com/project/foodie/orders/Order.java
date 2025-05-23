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
@Setter
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = true)
    private User user;

    private String guestSessionId;

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
