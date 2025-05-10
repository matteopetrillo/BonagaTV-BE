package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.config.security.CustomPasswordEncoder;
import it.petrillo.bonagatv.dao.EventoRepository;
import it.petrillo.bonagatv.dao.UtenteLiveRepository;
import it.petrillo.bonagatv.models.Evento;
import it.petrillo.bonagatv.models.UtenteLive;
import it.petrillo.bonagatv.models.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteLiveRepository utenteLiveRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public Long registraUtente(UserRegistrationDto registrationDetails) {
        try {
            UtenteLive nuovoUtente = new UtenteLive();
            nuovoUtente.setEmail(registrationDetails.getEmail());
            Optional<Evento> eventoOp = eventoRepository.findById(registrationDetails.getIdEvento());
            eventoOp.ifPresent(nuovoUtente::setEvento);
            String psw = RandomStringUtils.randomNumeric(7);
            nuovoUtente.setPassword(passwordEncoder.encode(psw));
            Long idUtente = utenteLiveRepository.saveAndFlush(nuovoUtente).getId();
            log.info("Inserito con successo l'utente: "+registrationDetails.getEmail()+" e gli è stato assegnato l'id "+idUtente);
            return idUtente;
        } catch (Exception e) {
            log.error("Errore nella registrazione dell'utente "+registrationDetails.getEmail());
            throw new RuntimeException();
        }
    }

    public Long isEmailAvailable(String email) {
        Optional<UtenteLive> utenteOp = utenteLiveRepository.getUtenteValidByEmail(email);
        log.info("Controllata disponibilita per la mail "+email);
        if (utenteOp.isPresent())
            return utenteOp.get().getId();
        return 0L;
    }
    public void eliminaUtente(Long id) {
        utenteLiveRepository.deleteById(id);
    }

    private String aggiungiUtenteAttivo(Long idUtente, String idSessione) throws RuntimeException {
        Optional<UtenteLive> utenteOp = utenteLiveRepository.findById(idUtente);
        if (utenteOp.isPresent()) {
            UtenteLive utente = utenteOp.get();
            utente.setSessioneUtente(idSessione);
            utenteLiveRepository.saveAndFlush(utente);
            return utente.getEmail();
        } else {
            log.error("Errore in aggiungiUtenteAttivo. UtenteVod non trovato nel database e non associabile ad una sessione");
            throw new RuntimeException("Errore in aggiungiUtenteAttivo");
        }

    }

    public void inoltraCredenziali(Long idUtente, String lang) {
        Optional<UtenteLive> utenteOp = utenteLiveRepository.findById(idUtente);
        if (utenteOp.isPresent()) {
            UtenteLive utente = utenteOp.get();
            CustomPasswordEncoder customEncoder = (CustomPasswordEncoder) passwordEncoder;
            try {
                emailService.sendEmail(utente.getEmail(), customEncoder.decode(utente.getPassword()), utente.getEvento().getNome(), lang);
                log.info("Email con le credenziali inviata a "+utente.getEmail());
            } catch (Exception e) {
                log.error("Errore nell'invio delle credenziali all'utente "+idUtente+". "+e.getMessage());

            }

        } else {
            log.error("UtenteVod a cui inviare le credenziali non valido.");
            throw new RuntimeException();
        }
    }

    private String eliminaUtenteAttivo(String idSessione) throws RuntimeException {
        Optional<UtenteLive> utenteOp = utenteLiveRepository.findBySessioneUtente(idSessione);
        if (utenteOp.isPresent()) {
            UtenteLive utente = utenteOp.get();
            utente.setSessioneUtente(null);
            utenteLiveRepository.saveAndFlush(utente);
            return utente.getEmail();
        } else {
            log.warn("Errore in eliminaUtenteAttivo. UtenteVod non trovato nel database e non associabile ad una sessione. IdSessione "+idSessione);
        }
        return null;
    }

    @Transactional
    @EventListener
    public void onConnectEvent(SessionConnectEvent event) {
        SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String idSessione = null;
        try {
            Long idUtente = Long.valueOf(Objects.requireNonNull(header.getFirstNativeHeader("idUtente")));
            idSessione = header.getSessionId();
            String emailUtente = aggiungiUtenteAttivo(idUtente, idSessione);
            log.info("L'utente " + emailUtente + " è connesso al sistema con idSessione " + idSessione + ".");
        } catch (Exception e) {
            log.error("Errore nella registrazione della sessione utente. Impossibile il collegamento alla sessione "+idSessione +
                    ". " +"Headers: " + header.toNativeHeaderMap());
        }
    }

    @Transactional
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String idSessione = null;
        try {
            idSessione = header.getSessionId();
            String emailUtente = eliminaUtenteAttivo(idSessione);
            log.info("L'utente "+emailUtente+" è disconnesso dal sistema.");
        } catch (Exception e) {
            log.error("Errore nella cancellazione della sessione dal db per la sessione "+idSessione+". Headers: "+header.toNativeHeaderMap());
        }

    }


}
