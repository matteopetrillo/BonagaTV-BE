package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.config.security.UserDetailsCustom;
import it.petrillo.bonagatv.exception.AlreadyLoggedException;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import it.petrillo.bonagatv.models.dto.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                log.info("Login di: " + loginRequest.getEmail());
                return response;
            }
        } catch (BadCredentialsException e) {
            log.error("Errore di Autenticazione. Utente "+loginRequest.getEmail()+" non presente nel DB.");
            throw new BadCredentialsException("Utente non presente nel DB",e.getCause());
        } catch (InternalAuthenticationServiceException e) {
            log.warn("Errore di Autenticazione. L'utente "+loginRequest.getEmail()+" sta tentando l'accesso da un altro" +
                    "dispositivo.");
            throw new InternalAuthenticationServiceException("Utente sta tentando l'accesso da un secondo dispositivo", e.getCause());
        } catch (Exception e) {
            log.error("Errore imprevisto durante il /login di: "+loginRequest.getEmail(), e.getMessage());
            throw new RuntimeException("Errore imprevisto nel login", e.getCause());
        }

        return null;
    }
}
