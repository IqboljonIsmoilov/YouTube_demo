package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.RegistrationDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {


    private final AuthService authService;
    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @ApiOperation(value = "login", notes = "Mathod used for login and getting taken", nickname = "nickname")
    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody @Valid AuthDTO dto) {
        log.info("Authorization: {}", dto);
        return ResponseEntity.ok(authService.login(dto));
    }


    @ApiOperation(value = "registration", notes = "Mathod used for registration", nickname = "nickname")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO dto) {
        log.info("Registration: {}", dto);
        return ResponseEntity.ok(authService.registration(dto));
    }


    @ApiOperation(value = "verification", notes = "Mathod used for verification", nickname = "nickname")
    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        log.info("Verification: {}", jwt);
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
