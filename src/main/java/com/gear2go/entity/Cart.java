package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {

    @Id
    @Column(name = "cart_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @OneToMany(targetEntity = CartItem.class,
            mappedBy = "cart",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> cartItemList = new ArrayList<>();

    private BigDecimal totalProductPrice = new BigDecimal(BigInteger.ZERO);

    public Cart(User user, LocalDate rentDate, LocalDate returnDate) {
        this.user = user;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
