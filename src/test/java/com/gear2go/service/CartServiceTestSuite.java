package com.gear2go.service;

import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.entity.Cart;
import com.gear2go.entity.CartItem;
import com.gear2go.entity.Product;
import com.gear2go.entity.User;
import com.gear2go.entity.enums.Role;
import com.gear2go.repository.CartRepository;
import com.gear2go.repository.ProductRepository;
import com.gear2go.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartServiceTestSuite {
    @Autowired
    private CartService cartService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private CartRepository cartRepository;

    @Test
    void shouldCreateCartAndAddCartItem() throws Exception {
        //Given
        User user = new User("Antonio", "Antonello", "aa@mail.com", "pass123", Role.ADMIN);
        Product product = new Product(1L, "P1", 2.4F, BigDecimal.valueOf(43.12), 15);
        when(authenticationService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        AddProductToCartRequest request = new AddProductToCartRequest(1L, 15);
        when(productService.checkProductAvailabilityInDateRange(request.productId(), LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(15);

        //When
        CartResponse resultResponse = cartService.addOrSubtractProductToCart(request);

        //Then

        assertEquals(1, resultResponse.cartItemResponseList().size());
        assertEquals(15, resultResponse.cartItemResponseList().getFirst().quantity());
        assertEquals(new BigDecimal("646.80"), resultResponse.cartItemResponseList().getFirst().price());
    }

    @Test
    void shouldAddCartItemToExistingCart() throws Exception {
        //Given
        User user = new User("Antonio", "Antonello", "aa@mail.com", "pass123", Role.ADMIN);
        Product product = new Product(1L, "P1", 2.4F, BigDecimal.valueOf(43.12), 15);
        Product product2 = new Product(2L, "P2", 1.2F, BigDecimal.valueOf(1.0), 5);
        Cart cart = new Cart(user, LocalDate.now(), LocalDate.now().plusDays(1));

        AddProductToCartRequest request = new AddProductToCartRequest(2L, 3);
        List<CartItem> cartItemList = new ArrayList<>();

        cartItemList.add(new CartItem(1L, 8, BigDecimal.valueOf(129.36), product, cart));
        cart.setCartItemList(cartItemList);
        user.setCart(cart);

        when(authenticationService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(productService.checkProductAvailabilityInDateRange(request.productId(), LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(15);

        //When
        CartResponse resultResponse = cartService.addOrSubtractProductToCart(request);

        //Then

        assertEquals(2, resultResponse.cartItemResponseList().size());
        assertEquals(8, resultResponse.cartItemResponseList().getFirst().quantity());
        assertEquals(3, resultResponse.cartItemResponseList().getLast().quantity());
        assertEquals(new BigDecimal("129.36"), resultResponse.cartItemResponseList().getFirst().price());
        assertEquals(new BigDecimal("3.0"), resultResponse.cartItemResponseList().getLast().price());
    }

    @Test
    void shouldRemoveCartItemFromCart() throws Exception {
        //Given
        User user = new User("Antonio", "Antonello", "aa@mail.com", "pass123", Role.ADMIN);
        Product product = new Product(1L, "P1", 2.4F, BigDecimal.valueOf(43.12), 15);
        Cart cart = new Cart(user, LocalDate.now(), LocalDate.now().plusDays(1));
        AddProductToCartRequest request = new AddProductToCartRequest(1L, -12);
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(new CartItem(1L, 3, BigDecimal.valueOf(129.36), product, cart));
        cart.setCartItemList(cartItemList);
        user.setCart(cart);

        when(authenticationService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productService.checkProductAvailabilityInDateRange(request.productId(), LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(4);

        //When
        CartResponse resultResponse = cartService.addOrSubtractProductToCart(request);

        //Then
        assertEquals(0, resultResponse.cartItemResponseList().size());
    }

    @Test
    void shouldUpdateCartRentDates() throws Exception {
        //Given
        User user = new User("Antonio", "Antonello", "aa@mail.com", "pass123", Role.ADMIN);
        Cart cart = new Cart(user, LocalDate.now(), LocalDate.now().plusDays(1));

        UpdateCartRentDatesRequest request = new UpdateCartRentDatesRequest(LocalDate.now(), LocalDate.now().plusDays(5));

        cart.setCartItemList(new ArrayList<>());
        user.setCart(cart);

        when(authenticationService.getAuthenticatedUser()).thenReturn(Optional.of(user));

        //When
        CartResponse resultCart = cartService.updateCartRentDates(request);
        int timeDifference = Period.between(resultCart.rentDate(), resultCart.returnDate()).getDays();

        //Then
        assertEquals(5, timeDifference);
    }
}
