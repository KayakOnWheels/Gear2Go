package com.gear2go.service;

import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.CreateCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import com.gear2go.entity.CartItem;
import com.gear2go.entity.Product;
import com.gear2go.mapper.CartMapper;
import com.gear2go.repository.CartItemRepository;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.ProductRepository;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;
    
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

    public CartResponse updateCartRentDates(Long id, UpdateCartRentDatesRequest updateCartRentDatesRequest) {
        Cart cart = cartRepository.findById(id).orElseThrow();

        cart.setRentDate(updateCartRentDatesRequest.rentDate());
        cart.setReturnDate(updateCartRentDatesRequest.returnDate());

        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }


    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cartRepository.delete(cart);
    }

    public CartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest) {
        Cart cart = cartRepository.findById(addProductToCartRequest.cartId()).orElseThrow();
        Product product = productRepository.findById(addProductToCartRequest.productId()).orElseThrow();

        if(productService.checkProductAvailabilityInDateRange(addProductToCartRequest.productId(), cart.getRentDate(), cart.getReturnDate()) <= 0) {
            throw new RuntimeException();
        }

        CartItem cartItem = new CartItem(addProductToCartRequest.quantity(), product.getPrice(), product.getWeight(), product, cart);
        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponse(cart);
    }

}
