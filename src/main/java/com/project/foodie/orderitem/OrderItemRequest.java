package com.project.foodie.orderitem;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemRequest {
    private Long itemId;
    private int quantity; // quantity of the item user add in cart
    private String guestSessionId; // null if logged in user
}
