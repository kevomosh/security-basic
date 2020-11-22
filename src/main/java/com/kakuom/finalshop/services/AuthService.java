package com.kakuom.finalshop.services;

import com.kakuom.finalshop.messages.JwtResponse;
import com.kakuom.finalshop.views.LoginView;
import com.kakuom.finalshop.views.RegisterView;

public interface AuthService {
    String registerUser(RegisterView registerView);
    JwtResponse loginUser(LoginView loginView);
}
