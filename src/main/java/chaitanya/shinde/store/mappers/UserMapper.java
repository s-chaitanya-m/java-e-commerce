package chaitanya.shinde.store.mappers;


import chaitanya.shinde.store.dtos.RegisterUserRequest;
import chaitanya.shinde.store.dtos.UserDto;
import chaitanya.shinde.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
}
