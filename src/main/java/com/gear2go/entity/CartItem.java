package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(name = "cart_item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(Integer quantity, BigDecimal price, Float weight, Product product, Cart cart) {
        this.quantity = quantity;
        this.price = price;
        this.weight = weight;
        this.product = product;
        this.cart = cart;
    }
}
