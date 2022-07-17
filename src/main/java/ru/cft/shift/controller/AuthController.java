package ru.cft.shift.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.domain.UserInfo;
import ru.cft.shift.dto.UserDTO;
import ru.cft.shift.entity.UserEntity;
import ru.cft.shift.exception.*;
import ru.cft.shift.service.UserService;
import ru.cft.shift.utils.BcryptGenerator;
import ru.cft.shift.utils.SecurityContextHelper;

@Api
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/sing-up")
    public ResponseEntity<UserDTO> register(
            @RequestBody UserInfo userInfo
            ) throws
            EmailAlreadyRegisteredException
    {
        return ResponseEntity.ok(
                userService.createUser(userInfo));
    }

    @PostMapping("/sing-out")
    public ResponseEntity<?> logout() {
        SecurityContextHelper.setNotAuthenticated();
        return ResponseEntity.ok().body("You've been singed out!");
    }

    @PostMapping("/sing-in")
    public ResponseEntity<UserDTO> login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password
    ) throws IncorrectLoginOrPasswordException {

        UserEntity user = userService.findUserByEmail(email);
        if(user == null){
            throw new IncorrectLoginOrPasswordException();
        }

        String existingPassword = user.getPassword();

        if(!BcryptGenerator.decode(password, existingPassword)){
            throw new IncorrectLoginOrPasswordException();
        }

        SecurityContextHelper.setAuthenticated(new UsernamePasswordAuthenticationToken(email, password));

        UserDTO userDTO = userService.getCurrentUser();
        if(userDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userDTO);
    }
}