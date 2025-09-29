package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.dtos.UserDto;
import chaitanya.shinde.store.entities.User;
import chaitanya.shinde.store.mappers.UserMapper;
import chaitanya.shinde.store.repositories.UserRepository;
import lombok.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users") //common route - all routes here will be relative to this route
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto>getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
//        return new ResponseEntity<>(user, HttpStatus.OK);

        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
