package com.kakuom.finalshop.services;

import com.kakuom.finalshop.messages.JwtResponse;
import com.kakuom.finalshop.model.Role;
import com.kakuom.finalshop.model.User;
import com.kakuom.finalshop.repo.UserRepository;
import com.kakuom.finalshop.security.JwtProperties;
import com.kakuom.finalshop.security.JwtProvider;
import com.kakuom.finalshop.views.LoginView;
import com.kakuom.finalshop.views.RegisterView;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService{
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder encoder;
    private JwtProvider jwtProvider;
    private EmailValidator emailValidator;


    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           PasswordEncoder encoder, JwtProvider jwtProvider,
                           EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.emailValidator = emailValidator;
    }

    @Override
    public String registerUser(RegisterView registerView) {
        if(userRepository.existsByEmail(registerView.getEmail()))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");

        if(userRepository.existsByName(registerView.getName()))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already exists");

        if(!emailValidator.test(registerView.getEmail()))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter valid email");

        User newUser = new User(registerView.getName(), registerView.getEmail(),
                encoder.encode(registerView.getPassword()));

        var userRole = registerView.getRole();
        if(StringUtils.hasText(userRole) && userRole.equals(JwtProperties.ADMIN_CODE)){
            newUser.setRole(Role.ADMIN);
        } else {
            newUser.setRole(Role.USER);
        }
        userRepository.save(newUser);

        return "user registered";
    }

    @Override
    public JwtResponse loginUser(LoginView loginView) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginView.getEmail(),
                        loginView.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponse(jwt, userDetails.getUsername());
    }
}
