package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
    private User user;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @OneToMany(targetEntity = CartItem.class,
            mappedBy = "cart",
            fetch = FetchType.LAZY)
    private List<CartItem> cartItemList;

}
