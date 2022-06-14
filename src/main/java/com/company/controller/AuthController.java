package com.company.controller;

import dto.AuthDTO;
import dto.RegistrationDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {


    private final AuthService authService;

    @ApiOperation(value = "login", notes = "Mathod used for login and getting taken")
    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody @Valid AuthDTO requestDTO) {
        log.info("Authorization: {}{}", requestDTO, AuthController.class);
        return ResponseEntity.ok(authService.login(requestDTO));
    }


    @ApiOperation(value = "registration", notes = "Mathod used for registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO requestDTO) {
        log.info("Registration: {}", requestDTO, AuthController.class);
        return ResponseEntity.ok(authService.registration(requestDTO));
    }


    @ApiOperation(value = "verification", notes = "Mathod used for verification")
    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        log.info("Verification: {}", jwt);
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
