package com.marketplace.tpo.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marketplace.tpo.demo.controllers.auth.AuthenticationRequest;
import com.marketplace.tpo.demo.controllers.auth.AuthenticationResponse;
import com.marketplace.tpo.demo.controllers.auth.RegisterRequest;
import com.marketplace.tpo.demo.controllers.config.JwtService;
import com.marketplace.tpo.demo.entity.User;
import com.marketplace.tpo.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository repository;  // Repositorio de usuarios en el que se verifica si el usuario existe en la base de datos (por email)
        private final PasswordEncoder passwordEncoder;   //Hashea nuestras contraseñas antes de guardarlas
        private final JwtService jwtService;            //se encarga de generar y validar los tokens JWT
        private final AuthenticationManager authenticationManager;   //se encarga de autenticar a los usuarios

        public AuthenticationResponse register(RegisterRequest request) {  // Maneja el registro de nuevos usuarios y lo guarda en la base de datos
                var user = User.builder()
                                .firstName(request.getFirstname())
                                .lastName(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole())
                                .build();

                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)  //se le devuelve token generado (en la clase JWTService) al usuario
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)  //se devuelve un token cada vez que el usuario inicia sesión
                                .build();
        }
}
