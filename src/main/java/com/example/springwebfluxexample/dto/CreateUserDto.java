package com.example.springwebfluxexample.dto;

import com.example.springwebfluxexample.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    private String username;

    private String password;

    private Role role;
}
