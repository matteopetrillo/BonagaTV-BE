package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.UtenteAbbonatoRepository;
import it.petrillo.bonagatv.models.UtenteAbbonato;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteAbbonatoRepository utenteAbbonatoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String aggiungiUtenteAttivo(Long idUtente, String idSessione) throws RuntimeException {
        Optional<UtenteAbbonato> utenteOp = utenteAbbonatoRepository.findById(idUtente);
        if (utenteOp.isPresent()) {
            UtenteAbbonato utente = utenteOp.get();
            utente.setSessioneUtente(idSessione);
            utenteAbbonatoRepository.saveAndFlush(utente);
            return utente.getEmail();
        } else {
            log.error("Utente non trovato nel database e non associabile ad una sessione");
            throw new RuntimeException("Errore in aggiungiUtenteAttivo");
        }

    }

    @Transactional
    public String eliminaUtenteAttivo(String idSessione) throws RuntimeException {
        Optional<UtenteAbbonato> utenteOp = utenteAbbonatoRepository.findBySessioneUtente(idSessione);
        if (utenteOp.isPresent()) {
            UtenteAbbonato utente = utenteOp.get();
            utente.setSessioneUtente(null);
            utenteAbbonatoRepository.saveAndFlush(utente);
            return utente.getEmail();
        } else {
            log.error("Utente non trovato nel database e non associabile ad una sessione");
            throw new RuntimeException("Errore in aggiungiUtenteAttivo");
        }
    }

    public void registraNuovoUtente(String email) {
        
    }

    @EventListener
    public void onConnectEvent(SessionConnectEvent event) {
        SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long idUtente = Long.valueOf(Objects.requireNonNull(header.getFirstNativeHeader("idUtente")));
        String idSessione = header.getSessionId();
        try {
            String emailUtente = aggiungiUtenteAttivo(idUtente,idSessione);
            log.info("L'utente "+emailUtente+" è connesso al sistema.");
        } catch (Exception e) {
            log.error("Errore nell'inserimento della sessione "+idSessione);
        }
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String idSessione = header.getSessionId();
        try {
            String emailUtente = eliminaUtenteAttivo(idSessione);
            log.info("L'utente "+emailUtente+" è disconnesso dal sistema.");
        } catch (Exception e) {
            log.error("Errore nella cancellazione della sessione dal db");
        }

    }


}
