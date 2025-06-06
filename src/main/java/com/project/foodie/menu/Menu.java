package com.project.foodie.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String itemId;
    private String itemName;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private boolean available = true;

    public Menu() {}
}
