package com.proj.visionaly.controller;

import com.proj.visionaly.models.Role;
import com.proj.visionaly.models.User;
import com.proj.visionaly.models.UserInfo;
import com.proj.visionaly.repository.IRoleRepository;
import com.proj.visionaly.repository.IUserRepository;
import com.proj.visionaly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController("v1_authController")
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;


    @PostMapping("signin")
    public ResponseEntity<String> authenticateUser(@RequestBody User userData){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userData.getUsername(), userData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    //TODO
    // надо подумать, добавлять ли прямо сюда создание UserInfo, или в другой ендпоинт - продолжение регистрации

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody User userData){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(userData.getUsername())){
            return new ResponseEntity<>(
                    String.format("User with username = %s is already exists. Please choose another username.", userData.getUsername()),
                    HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(userData.getEmail())){
            return new ResponseEntity<>(
                    "User this email is already registered.",
                    HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        //user.setName(signUpDto.getName());
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(Collections.singletonList(roles));

        userService.saveUser(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

    @PostMapping("signup/user_info")
    public ResponseEntity<?> fillUserInformation(@RequestBody UserInfo userInfo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        user.setUserInfo(userInfo);
        userService.saveUser(user);

        return new ResponseEntity<>("User information added successfully", HttpStatus.OK);
    }
}
