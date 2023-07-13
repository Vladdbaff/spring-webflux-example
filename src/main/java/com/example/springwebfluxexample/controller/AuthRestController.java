package com.example.springwebfluxexample.controller;


import com.example.springwebfluxexample.dto.AuthRequestDto;
import com.example.springwebfluxexample.dto.AuthResponseDto;
import com.example.springwebfluxexample.security.JwtUtil;
import com.example.springwebfluxexample.security.MyPasswordEncoder;
import com.example.springwebfluxexample.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class AuthRestController {

    private JwtUtil jwtUtil;
    private MyPasswordEncoder passwordEncoder;
    private UserService userService;


    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDto>> login(@RequestBody AuthRequestDto ar) {
        System.out.println(ar);
        System.out.println(userService.findByUsername(ar.getUsername()).block());
        System.out.println(passwordEncoder.encode(ar.getPassword()));
        return userService.findByUsername(ar.getUsername())
                .filter(userDetails -> passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponseDto(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
