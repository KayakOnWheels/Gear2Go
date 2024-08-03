package com.gear2go.service;

import com.gear2go.dto.request.MailRequest;
import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import com.gear2go.entity.CartItem;
import com.gear2go.entity.Product;
import com.gear2go.entity.User;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.exception.ProductNotAvailable;
import com.gear2go.exception.UserNotFoundException;
import com.gear2go.mapper.CartMapper;
import com.gear2go.repository.CartItemRepository;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.ProductRepository;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
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
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final AuthenticationService authenticationService;
    private final CartMapper cartMapper;

    public List<CartResponse> getAllCarts() {
        return cartMapper.toCartResponseList(cartRepository.findAll());
    }

    public CartResponse getUserCart(@Nullable MailRequest mailRequest) throws ExceptionWithHttpStatusCode {
        User cartOwner;
        Cart cart;

        cartOwner = (mailRequest == null) ? getCartOwner(null) : getCartOwner(mailRequest.mail());

        cart = cartOwner.getCart();

        return cartMapper.toCartResponse(cart);
    }

    public CartResponse updateCartRentDates(UpdateCartRentDatesRequest updateCartRentDatesRequest) throws ExceptionWithHttpStatusCode {

        User cartOwner = getCartOwner(updateCartRentDatesRequest.mail());
        Cart cart = cartOwner.getCart();

        cart.setRentDate(updateCartRentDatesRequest.rentDate());
        cart.setReturnDate(updateCartRentDatesRequest.returnDate());

        updateTotalProductPrice(cart);
        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }


    public CartResponse addOrSubtractProductToCart(AddProductToCartRequest addProductToCartRequest) throws ExceptionWithHttpStatusCode {
        User cartOwner;
        Cart cart;

        cartOwner = getCartOwner(addProductToCartRequest.userMail());
        Product product = productRepository.findById(addProductToCartRequest.productId()).orElseThrow();

        if (cartOwner.getCart() == null) {
            cartOwner.setCart(new Cart(cartOwner, LocalDate.now(), LocalDate.now().plusDays(1)));
        }
        cart = cartOwner.getCart();

        if (productService.checkProductAvailabilityInDateRange(addProductToCartRequest.productId(), cart.getRentDate(), cart.getReturnDate()) <= 0) {
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

        cartItemRepository.save(cartItem);

        updateTotalProductPrice(cart);
        return cartMapper.toCartResponse(cart);
    }


    public void updateTotalProductPrice(Cart cart) {
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        for (CartItem cartItem : cart.getCartItemList()) {
            totalPrice = totalPrice.add(cartItem.getPrice());
        }
        long period = ChronoUnit.DAYS.between(cart.getRentDate(), cart.getReturnDate());
        totalPrice = totalPrice.multiply(BigDecimal.valueOf(period));

        cart.setTotalProductPrice(totalPrice);
        cartRepository.save(cart);
    }

    private User getCartOwner(@Nullable String mail) throws ExceptionWithHttpStatusCode {
        Authentication authentication = authenticationService.getAuthentication();
        User currentUser = userRepository.findUserByMail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        String email = (mail == null) ? currentUser.getMail() : mail;
        return userRepository.findUserByMail(email).orElseThrow(UserNotFoundException::new);
    }
}
