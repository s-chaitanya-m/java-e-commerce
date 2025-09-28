package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.entities.User;
import chaitanya.shinde.store.repositories.UserRepository;
import lombok.*;
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

    @GetMapping
    public Iterable<User>getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
