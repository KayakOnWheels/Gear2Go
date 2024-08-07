package com.gear2go.repository;

import com.gear2go.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
}
