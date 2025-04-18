package com.springlessons.testproject.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.springlessons.testproject.model.UserRule;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.springlessons.testproject.model.Users;
import com.springlessons.testproject.repositories.UsersRepository;
import com.springlessons.testproject.security.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtUtil jwtUtil,
                         UsersRepository usersRepository,
                         PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRule(UserRule.ROLE_USER);
        usersRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Users authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        final UserDetails userDetails = usersRepository.findByUsername(authenticationRequest.getUsername())
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserRule()))
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));

        final String jwt = jwtUtil.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

//    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin(Principal principal,
                                         @AuthenticationPrincipal UserDetails user,
                                         HttpServletRequest httpServletRequest) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();
        System.out.println(username);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getAuthorities());

        System.out.println(principal.getName());
        System.out.println(authentication.getName());
        System.out.println(user.getUsername());

        Principal principal1 = httpServletRequest.getUserPrincipal();
        System.out.println(principal1.getName());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Create admin user");
        return ResponseEntity.ok(response);
    }

} 