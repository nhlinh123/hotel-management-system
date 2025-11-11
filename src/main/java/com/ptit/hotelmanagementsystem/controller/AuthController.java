package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.AuthenticationRequest;
import com.ptit.hotelmanagementsystem.dto.AuthenticationResponse;
import com.ptit.hotelmanagementsystem.dto.BaseResponseModel;
import com.ptit.hotelmanagementsystem.dto.RegisterRequest;
import com.ptit.hotelmanagementsystem.model.User;
import com.ptit.hotelmanagementsystem.repository.UserRepository;
import com.ptit.hotelmanagementsystem.service.UserService;
import com.ptit.hotelmanagementsystem.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseModel<AuthenticationResponse>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponseModel.unauthorized("Incorrect username or password"));
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(BaseResponseModel.success(new AuthenticationResponse(jwt), "Login successful"));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponseModel<String>> registerUser(@RequestBody RegisterRequest registerRequest) throws Exception {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseModel.badRequest("Username already exists"));
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRoles(registerRequest.getRoles() != null ? registerRequest.getRoles() : "ROLE_USER");

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseModel.created("User registered successfully"));
    }
}
