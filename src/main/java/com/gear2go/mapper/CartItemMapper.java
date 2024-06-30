package com.gear2go.mapper;

import com.gear2go.dto.response.CartItemResponse;
import com.gear2go.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemMapper {

    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getWeight(),
                cartItem.getProduct().getId(),
                cartItem.getCart().getId()
        );
    }

    public List<CartItemResponse> toCartItemList(List<CartItem> cartItemList) {
        return cartItemList.stream()
                .map(this::toCartItemResponse)
                .toList();
    }
}
