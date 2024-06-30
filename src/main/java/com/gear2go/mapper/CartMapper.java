package com.gear2go.mapper;

import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                cart.getRentDate(),
                cart.getReturnDate()
        );
    }

    public List<CartResponse> toCartList(List<Cart> cartList) {
        return cartList.stream()
                .map(this::toCartResponse)
                .toList();
    }
}
