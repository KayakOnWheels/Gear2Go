package com.gear2go.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="auth_token")
    private Integer authToken;

    @OneToMany(targetEntity = Order.class,
            mappedBy = "user",
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Order> orderList = new ArrayList<>();


    @OneToMany(targetEntity = Address.class,
            mappedBy = "user",
            fetch = FetchType.LAZY)
    private List<Address> addressList;

    public User(String firstName, String lastName, String mail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }
}
