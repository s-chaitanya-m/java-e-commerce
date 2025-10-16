package chaitanya.shinde.store.service;

import chaitanya.shinde.store.dtos.AddItemToCartRequest;
import chaitanya.shinde.store.dtos.CartDto;
import chaitanya.shinde.store.dtos.CartItemDto;
import chaitanya.shinde.store.entities.Cart;
import chaitanya.shinde.store.exceptions.CartNotFoundException;
import chaitanya.shinde.store.exceptions.ProductNotFoundException;
import chaitanya.shinde.store.mappers.CartMapper;
import chaitanya.shinde.store.repositories.CartRepository;
import chaitanya.shinde.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto getCart (UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        return cartMapper.toDto(cart);
    }

    public CartDto createCart () {
        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart (UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) throw new ProductNotFoundException();

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartItemDto updateCartItem (UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        var cartItem = cart.getItem(productId);
        if (cartItem == null) throw new ProductNotFoundException();

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void deleteCartItem (UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        cart.removeCartItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart (UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        cart.clearCart();
        cartRepository.save(cart);
    }
}
