package com.gear2go.mapper;

import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartResponse toCartResponse(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                cart.getRentDate(),
                cart.getReturnDate(),
                cartItemMapper.toCartItemList(cart.getCartItemList()),
                cart.getTotalProductPrice()
        );
    }

    public List<CartResponse> toCartResponseList(List<Cart> cartList) {
        return cartList.stream()
                .map(this::toCartResponse)
                .toList();
    }
}
