package it.petrillo.bonagatv.config.security;

import it.petrillo.bonagatv.dao.UtenteAbbonatoRepository;
import it.petrillo.bonagatv.exception.AlreadyLoggedException;
import it.petrillo.bonagatv.models.UtenteAbbonato;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    private UtenteAbbonatoRepository utenteAbbonatoRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UtenteAbbonato> utenteAbbonatoOptional = utenteAbbonatoRepository.getLoggableByEmail(username);
        if (utenteAbbonatoOptional.isPresent()) {
            UtenteAbbonato utente = utenteAbbonatoOptional.get();
            if (utente.getSessioneUtente() == null)
                return new UserDetailsCustom(utenteAbbonatoOptional.get());
            else
                throw new AlreadyLoggedException("Utente già loggato");
        }
        else {
            throw new UsernameNotFoundException("Utente non trovato");
        }

    }
}
