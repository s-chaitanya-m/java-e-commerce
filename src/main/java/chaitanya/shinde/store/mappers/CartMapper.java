package chaitanya.shinde.store.mappers;

import chaitanya.shinde.store.dtos.CartDto;
import chaitanya.shinde.store.dtos.CartItemDto;
import chaitanya.shinde.store.entities.Cart;
import chaitanya.shinde.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
