package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NamedQuery(
        name = "Product.findNumberOfProductsRentedInDataRange",
        query = "SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.product.id = :ID AND ci.cart.rentDate >= :RENT_DATE AND ci.cart.returnDate <= :RETURN_DATE"
)
@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private float weight = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(targetEntity = CartItem.class,
            mappedBy = "product",
            fetch = FetchType.LAZY)
    private List<CartItem> cartItemList;

    public Product(String name, float weight, BigDecimal price, Integer stock) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.stock = stock;
    }
}
