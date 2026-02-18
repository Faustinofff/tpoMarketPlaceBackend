package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.controllers.auth.AuthenticationRequest;
import com.marketplace.tpo.demo.controllers.auth.AuthenticationResponse;
import com.marketplace.tpo.demo.controllers.auth.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
