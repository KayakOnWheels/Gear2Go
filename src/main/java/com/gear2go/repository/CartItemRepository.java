package com.gear2go.repository;

import com.gear2go.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

