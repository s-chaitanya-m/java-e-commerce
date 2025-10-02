package chaitanya.shinde.store.mappers;


import chaitanya.shinde.store.dtos.RegisterUserRequest;
import chaitanya.shinde.store.dtos.UpdateUserRequest;
import chaitanya.shinde.store.dtos.UserDto;
import chaitanya.shinde.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request,@MappingTarget User user);
}
