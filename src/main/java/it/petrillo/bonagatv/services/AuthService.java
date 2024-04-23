package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.config.security.UserDetailsCustom;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import it.petrillo.bonagatv.models.dto.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(),
                    loginRequest.getPassword());
            Authentication authResponse = this.authenticationManager.authenticate(authRequest);
            UserDetailsCustom utente = (UserDetailsCustom) authResponse.getPrincipal();
            LoginResponse response = new LoginResponse(utente.getUtente().getId());
            if (authResponse.isAuthenticated()) {
                log.info("Richiesta di login da: " + loginRequest.getEmail() + " approvata");
                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Errore durante il /login di: "+loginRequest.getEmail(), e.getMessage());
        }
        return null;
    }
}
