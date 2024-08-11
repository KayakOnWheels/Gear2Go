package com.gear2go.service;

import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import com.gear2go.entity.CartItem;
import com.gear2go.entity.Product;
import com.gear2go.entity.User;
import com.gear2go.exception.*;
import com.gear2go.mapper.CartMapper;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final AuthenticationService authenticationService;
    private final CartMapper cartMapper;

    public List<CartResponse> getAllCarts() {
        return cartMapper.toCartResponseList(cartRepository.findAll());
    }

    public CartResponse getUserCart() throws ExceptionWithHttpStatusCode {
        User cartOwner;
        Cart cart;

        cartOwner = authenticationService.getAuthenticatedUser().orElseThrow(UserNotFoundException::new);
        cart = cartOwner.getCart();

        return cartMapper.toCartResponse(cart);
    }

    public CartResponse updateCartRentDates(UpdateCartRentDatesRequest updateCartRentDatesRequest) throws ExceptionWithHttpStatusCode {

        User cartOwner = authenticationService.getAuthenticatedUser().orElseThrow(UserNotFoundException::new);
        Cart cart = cartOwner.getCart();

        cart.setRentDate(updateCartRentDatesRequest.rentDate());
        cart.setReturnDate(updateCartRentDatesRequest.returnDate());

        updateTotalProductPrice(cart);
        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }


    public CartResponse addOrSubtractProductToCart(AddProductToCartRequest addProductToCartRequest) throws ExceptionWithHttpStatusCode {
        if (addProductToCartRequest.quantity() >= 0) {
            return addProductToCart(addProductToCartRequest);
        }
        return subtractProductFromCart(addProductToCartRequest);
    }

    public CartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest) throws ExceptionWithHttpStatusCode {
        User cartOwner;
        Cart cart;

        cartOwner = authenticationService.getAuthenticatedUser().orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(addProductToCartRequest.productId()).orElseThrow(ProductNotFoundException::new);

        if (cartOwner.getCart() == null) {
            cartOwner.setCart(new Cart(cartOwner, LocalDate.now(), LocalDate.now().plusDays(1)));
        }
        cart = cartOwner.getCart();

        Integer availability = productService.checkProductAvailabilityInDateRange(addProductToCartRequest.productId(), cart.getRentDate(), cart.getReturnDate());
        if (availability < addProductToCartRequest.quantity()) {
            throw new ProductNotAvailable();
        }

        CartItem cartItem = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().
                orElse(new CartItem(0, BigDecimal.ZERO, product, cart));

        if (cartItem.getId() == null) {
            cart.getCartItemList().add(cartItem);
        }

        cartItem.setQuantity(cartItem.getQuantity() + addProductToCartRequest.quantity());
        cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        cart = updateTotalProductPrice(cart);
        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }

    public CartResponse subtractProductFromCart(AddProductToCartRequest addProductToCartRequest) throws ExceptionWithHttpStatusCode {
        User cartOwner;
        Cart cart;

        cartOwner = authenticationService.getAuthenticatedUser().orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(addProductToCartRequest.productId()).orElseThrow(ProductNotFoundException::new);

        if (cartOwner.getCart() == null) {
            throw new CartNotFoundException();
        }

        cart = cartOwner.getCart();
        CartItem cartItem = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().
                orElseThrow(CartItemNotFoundException::new);

        int newQuantity = cartItem.getQuantity() + addProductToCartRequest.quantity();
        if (newQuantity <= 0) {
            cart.getCartItemList().remove(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        cart = updateTotalProductPrice(cart);
        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }


    public Cart updateTotalProductPrice(Cart cart) {
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        for (CartItem cartItem : cart.getCartItemList()) {
            totalPrice = totalPrice.add(cartItem.getPrice());
        }
        long period = ChronoUnit.DAYS.between(cart.getRentDate(), cart.getReturnDate());
        totalPrice = totalPrice.multiply(BigDecimal.valueOf(period));

        cart.setTotalProductPrice(totalPrice);
        return cart;
    }
}
