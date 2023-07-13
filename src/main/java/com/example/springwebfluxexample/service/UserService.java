package com.example.springwebfluxexample.service;

import com.example.springwebfluxexample.model.User;
import com.example.springwebfluxexample.repository.UserRepository;
import com.example.springwebfluxexample.security.MyPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
