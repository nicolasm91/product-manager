package com.otsnd.productmanager.controller;

import com.otsnd.productmanager.constants.Constants;
import com.otsnd.productmanager.dto.response.UserDTO;
import com.otsnd.productmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<UserDTO> user = this.userService.findById(id);

        return user.isPresent() ? ResponseEntity.ok(user.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap(Constants.ERROR_MESSAGE, "user with id " + id + " not found"));
    }
}
