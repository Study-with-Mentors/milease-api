package vn.id.milease.mileaseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.exception.GoogleIdTokenVerificationFailedException;
import vn.id.milease.mileaseapi.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/google")
    public String googleLogin(@RequestBody String idToken) {
        return authenticationService.verifyIdToken(idToken);
    }
}
