package com.project.foodie.orderitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.project.foodie.menu.Menu;
import com.project.foodie.orders.Order;

@Entity
@Setter
@Getter
public class OrderItem {
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
