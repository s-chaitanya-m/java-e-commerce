package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.dtos.AddItemToCartRequest;
import chaitanya.shinde.store.dtos.CartItemDto;
import chaitanya.shinde.store.entities.CartItem;
import chaitanya.shinde.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import chaitanya.shinde.store.dtos.CartDto;
import chaitanya.shinde.store.entities.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import chaitanya.shinde.store.mappers.CartMapper;
import org.springframework.web.util.UriComponentsBuilder;
import chaitanya.shinde.store.repositories.CartRepository;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    public final CartRepository cartRepository;
    public final ProductRepository productRepository;
    public final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart (
            UriComponentsBuilder uriBuilder
    ) {
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);

        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart (
            @PathVariable(name = "cartId") UUID cartId,
            @RequestBody AddItemToCartRequest request
            ) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null ) return ResponseEntity.notFound().build();

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) return ResponseEntity.badRequest().build();

        var cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(request.getProductId())).findFirst().orElse(null);

        if (cartItem !=  null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);
        var cartItemDto = cartMapper.toDto(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }
}
