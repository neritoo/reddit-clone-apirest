package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.AuthenticationResponse;
import com.gavilan.redditapirest.dto.LoginRequest;
import com.gavilan.redditapirest.dto.RefreshTokenRequest;
import com.gavilan.redditapirest.dto.RegisterRequest;
import com.gavilan.redditapirest.service.AuthService;
import com.gavilan.redditapirest.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {

        /*
        Map<String, Object> response = new HashMap<>();

        try {

            authService.signup(registerRequest);
        } catch (SpringRedditException e) {
            response.put("message", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */

        return new ResponseEntity<>("Registración de usuario exitosa", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        /*
        Map<String, Object> response = new HashMap<>();
        try {

            authService.verifyAccount(token);
        } catch (SpringRedditException e) {
            response.put("message", "Error validando token");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */

        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

        /*
        Map<String, Object> response = new HashMap<>();
        AuthenticationResponse authenticationResponse;

        try {

            authenticationResponse = authService.refreshToken(refreshTokenRequest);
        } catch (SpringRedditException e) {
            response.put("message", "Error al refrescar token");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */

        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());

        return new ResponseEntity<>("Refresh token eliminado con éxito.", HttpStatus.OK);
    }
}
