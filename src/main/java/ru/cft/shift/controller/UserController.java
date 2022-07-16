package ru.cft.shift.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.shift.dto.UserDTO;
import ru.cft.shift.service.UserService;
import ru.cft.shift.utils.SecurityContextHelper;

@Api
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/account")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(){
        if(!userService.deleteUser()){
            return new ResponseEntity<>("Failed to delete user", HttpStatus.BAD_REQUEST);
        }

        SecurityContextHelper.setNotAuthenticated();
        return ResponseEntity.ok("Deleted");
    }
}
