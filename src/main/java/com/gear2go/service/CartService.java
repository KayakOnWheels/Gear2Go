package com.gear2go.service;

import com.gear2go.dto.request.cart.CreateCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import com.gear2go.mapper.CartMapper;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    
    CartRepository cartRepository;
    UserRepository userRepository;
    CartMapper cartMapper;
    
    public List<CartResponse> getAllCarts() {
        return cartMapper.toCartResponseList(cartRepository.findAll());
    }

    public CartResponse getCart(Long id) {
        return cartMapper.toCartResponse(cartRepository.findById(id).orElseThrow());
    }

    public CartResponse createEmptyCart(CreateCartRequest createCartRequest) {
        Cart cart = new Cart(
                userRepository.findById(createCartRequest.userId()).orElseThrow(),
                createCartRequest.rentDate(),
                createCartRequest.returnDate()
                );

        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }

    public CartResponse updateCart(UpdateCartRequest updateCartRequest) {
        Cart cart = cartRepository.findById(updateCartRequest.id()).orElseThrow();

        cart.setCartItemList(updateCartRequest.cartItemList());
        cart.setRentDate(updateCartRequest.rentDate());
        cart.setReturnDate(updateCartRequest.returnDate());

        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }

    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cartRepository.delete(cart);
    }
}
