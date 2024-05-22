package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.EventoRepository;
import it.petrillo.bonagatv.dao.UtenteAbbonatoRepository;
import it.petrillo.bonagatv.models.UtenteAbbonato;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteAbbonatoRepository utenteAbbonatoRepository;
    @Autowired
    private EventoRepository eventoRepository;

    public void addUtenteWithCustomPsw(LoginRequest loginRequest, Long idEvento) {
        try {
            Optional<UtenteAbbonato> utenteOp = utenteAbbonatoRepository.getUtenteValidByEmail(loginRequest.getEmail());
            if (utenteOp.isPresent()) {
                throw new IllegalArgumentException();
            }
            UtenteAbbonato utente = new UtenteAbbonato();
            utente.setEvento(eventoRepository.findById(idEvento).orElseThrow());
            utente.setEmail(loginRequest.getEmail());
            utente.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
            utenteAbbonatoRepository.saveAndFlush(utente);
            log.info("Aggiunto l'utente "+loginRequest.getEmail()+" bypassando il pagamento");

        } catch (IllegalArgumentException e) {
            log.error("Fallito l'aggiunta forzata dell'utente in addUtenteWithCustomPsw, utente già presente nel DB");
            throw new IllegalArgumentException();
        } catch (Exception e) {
            log.error("Fallito l'aggiunta forzata dell'utente in addUtenteWithCustomPsw, errore inaspettato");
            throw new RuntimeException("Fallito l'add forzato dell'utente");
        }
    }

    public void forceLogout(String email) {
        Optional<UtenteAbbonato> utenteOp = utenteAbbonatoRepository.getUtenteValidByEmail(email);
        if (utenteOp.isEmpty()) {
            log.error("Utente "+email+" non presente nel sistema");
            throw new UsernameNotFoundException("Utente non presente nel DB");
        }

        UtenteAbbonato utente = utenteOp.get();
        if (utente.getSessioneUtente() != null) {
            log.warn("Tentato il logout brute force di "+email+" ma l'utente non è collegato al sistema");
        }
        utente.setSessioneUtente(null);
        log.info("Forzato il logout per l'utente "+email);
        utenteAbbonatoRepository.saveAndFlush(utente);

    }


    @Transactional
    public void addMassiveUsers(List<LoginRequest> utenti, Long idEvento) {
        try {
            List<UtenteAbbonato> utentiDaAggiungere = new ArrayList<>();
            for (LoginRequest req : utenti) {
                UtenteAbbonato u = new UtenteAbbonato(req.getEmail().trim(), passwordEncoder.encode(req.getPassword()));
                u.setEvento(eventoRepository.findById(idEvento).orElseThrow());
                utentiDaAggiungere.add(u);
            }
            utenteAbbonatoRepository.saveAllAndFlush(utentiDaAggiungere);
            log.info("Aggiunti brute force "+utentiDaAggiungere.size()+" utenti");
        } catch (Exception e) {
            log.error("Errore nell'aggiunta massiva di utenti.", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
