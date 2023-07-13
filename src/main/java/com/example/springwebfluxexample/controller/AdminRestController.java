package com.example.springwebfluxexample.controller;


import com.example.springwebfluxexample.dto.CreateUserDto;
import com.example.springwebfluxexample.model.User;
import com.example.springwebfluxexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final UserService userService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> getUser(@RequestParam("username") String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().eTag("NOT FOUND").build()));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.createUser(createUserDto).map(ResponseEntity::ok);
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<User>>> getAllUsers() {
        return userService.getAllUsers()
                .collectList()
                .map(ResponseEntity::ok);
    }

}
