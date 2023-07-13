package com.example.springwebfluxexample.controller;


import com.example.springwebfluxexample.model.User;
import com.example.springwebfluxexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final UserService userService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> getUser(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().eTag("NOT FOUND").build()));
    }

    @GetMapping("/all")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
