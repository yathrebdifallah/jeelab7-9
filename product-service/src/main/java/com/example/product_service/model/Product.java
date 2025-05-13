package com.example.product_service.model;

public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;

    // ✅ Constructeur avec tous les champs
    public Product(Long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // ✅ Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    // (optionnel) Setters si tu veux modifier les valeurs plus tard
}

