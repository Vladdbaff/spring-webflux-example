package com.example.springwebfluxexample.service;

import com.example.springwebfluxexample.dto.CreateUserDto;
import com.example.springwebfluxexample.model.Role;
import com.example.springwebfluxexample.model.User;
import com.example.springwebfluxexample.security.MyPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private Map<String, User> data;

    private final MyPasswordEncoder myPasswordEncoder;

    @PostConstruct
    public void init() {
        data = new HashMap<>();

        //username:passwowrd -> user:user
        data.put("user", new User("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER)));

        //username:passwowrd -> admin:admin
        data.put("admin", new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN)));

        data.put("jojo", new User("jojo", myPasswordEncoder.encode("jojo"), true, Arrays.asList(Role.ROLE_USER)));
    }

    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(data.get(username));
    }


    public Mono<User> createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(myPasswordEncoder.encode(createUserDto.getPassword()));
        user.setRoles(Collections.singletonList(createUserDto.getRole()));
        user.setEnabled(true);
        data.put(user.getUsername(), user);
        return Mono.justOrEmpty(data.get(user.getUsername()));
    }

    public Flux<User> getAllUsers() {
        return Flux.fromIterable(data.values());
    }
}
