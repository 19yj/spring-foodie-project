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

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;
    private String itemId;
    private String itemName;
    private double price;
    private int quantity;
    private double totalPrice;

    public OrderItem() {}

}
