package com.project.foodie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class OrderItem {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    // links each item to its parent order
    @ManyToOne
    @JoinColumn(name = "orderId")
    // create a column named orderId in order_item table that store the foreign key pointing to id in the order table

    private Order order;
    private String foodId;
    private String foodName;
    private double price;
    private int quantity;
    private double totalPrice;

    public OrderItem() {}

}
