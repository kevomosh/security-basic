package com.kakuom.finalshop.controller;

import com.kakuom.finalshop.messages.JwtResponse;
import com.kakuom.finalshop.services.AuthService;
import com.kakuom.finalshop.views.LoginView;
import com.kakuom.finalshop.views.RegisterView;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterView registerView){
        return authService.registerUser(registerView);
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginView loginView) {
        return authService.loginUser(loginView);
    }
}
