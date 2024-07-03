package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
@NoArgsConstructor
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

    public Cart(User user, LocalDate rentDate, LocalDate returnDate) {
        this.user = user;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }
}
