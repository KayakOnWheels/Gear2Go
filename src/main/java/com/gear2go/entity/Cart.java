package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "cart_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User userId;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @OneToMany(targetEntity = CartItem.class,
            mappedBy = "cart",
            fetch = FetchType.LAZY)
    private List<CartItem> cartItemId;

}
