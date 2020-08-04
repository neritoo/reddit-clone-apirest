package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.dto.AuthenticationResponse;
import com.gavilan.redditapirest.dto.LoginRequest;
import com.gavilan.redditapirest.dto.RefreshTokenRequest;
import com.gavilan.redditapirest.dto.RegisterRequest;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.model.NotificationEmail;
import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.model.VerificationToken;
import com.gavilan.redditapirest.repository.UserRepository;
import com.gavilan.redditapirest.repository.VerificationTokenRepository;
import com.gavilan.redditapirest.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(new Date());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Porfavor, active su cuenta.",
                user.getEmail(), "Gracias por registrarse a reddit clone! Porfavor, haga click en" +
                " el url de aqui abajo para activar su cuenta: " +
                "\nhttps://reddit-clone-apirest.herokuapp.com/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;

    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));

        fetchUserAndEnable(verificationToken.get());

    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new SpringRedditException("User not found with name - " + loginRequest.getUsername()));

        if (user.getRole() == null) {
            throw new SpringRedditException("No Role found");
        }

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(new Date(new Date().getTime() + jwtProvider.getJwtExpirationInMillis()))
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getRoleName())
                .build();
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " +
                        principal.getUsername()));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .username(refreshTokenRequest.getUsername())
                .expiresAt(new Date(new Date().getTime() + jwtProvider.getJwtExpirationInMillis()))
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
