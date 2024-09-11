package com.drjava.journal.controller;

import org.springframework.web.bind.annotation.RestController;

import com.drjava.journal.entity.User;
import com.drjava.journal.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User>getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User old = userService.findByUserName(userName);
        if (old != null) {
            old.setUserName(user.getUserName());
            old.setPassword(user.getPassword());
            userService.saveEntry(old);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
