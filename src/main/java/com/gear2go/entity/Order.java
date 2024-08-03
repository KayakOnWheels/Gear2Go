package com.gear2go.entity;

import com.gear2go.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "status")
    private OrderStatus orderStatus;


    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "billing_address_id", nullable = false)
    private Address billingAddress;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Order(LocalDate orderDate, OrderStatus orderStatus, User user, Cart cart, Address billingAddress, Address shippingAddress) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.user = user;
        this.cart = cart;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }
}
