package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.dtos.AddItemToCartRequest;
import chaitanya.shinde.store.dtos.CartItemDto;
import chaitanya.shinde.store.dtos.UpdateCartItemRequest;
import chaitanya.shinde.store.entities.CartItem;
import chaitanya.shinde.store.exceptions.CartNotFoundException;
import chaitanya.shinde.store.exceptions.ProductNotFoundException;
import chaitanya.shinde.store.repositories.ProductRepository;
import chaitanya.shinde.store.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import chaitanya.shinde.store.dtos.CartDto;
import chaitanya.shinde.store.entities.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import chaitanya.shinde.store.mappers.CartMapper;
import org.springframework.web.util.UriComponentsBuilder;
import chaitanya.shinde.store.repositories.CartRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    public final CartRepository cartRepository;
    public final ProductRepository productRepository;
    public final CartMapper cartMapper;
    public final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts () {
        var cartList = cartRepository.getAllCartWithItems();
        return ResponseEntity.ok(cartList.stream().map(cartMapper::toDto).toList());
    }

    @GetMapping("/{cartId}")
    public CartDto getCart (
            @PathVariable(name = "cartId") UUID cartId
    ) {
        return cartService.getCart(cartId);
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart (
            UriComponentsBuilder uriBuilder
    ) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart (
            @PathVariable(name = "cartId") UUID cartId,
            @RequestBody AddItemToCartRequest request
            ) {
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ) {
        return cartService.updateCartItem(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> deleteCartItem (
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId
    ) {
       cartService.deleteCartItem(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart (
            @PathVariable(name = "cartId") UUID cartId
    ) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart Not Found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product Not Found"));
    }
}
