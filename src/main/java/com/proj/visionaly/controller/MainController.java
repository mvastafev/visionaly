package com.proj.visionaly.controller;

import com.proj.visionaly.models.User;
import com.proj.visionaly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/v1/api/users", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @RequestMapping(value="/", method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions()
    {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDetails> getUser(@PathVariable String username) {
        UserDetails user = userService.loadUserByUsername(username);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user/")
    public ResponseEntity<User> getUserDetails(@RequestParam String user_email) {
        User user = userService.findUserByEmail(user_email);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }
}
