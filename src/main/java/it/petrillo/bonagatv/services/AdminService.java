package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.EventoRepository;
import it.petrillo.bonagatv.dao.PasswordStandardRepository;
import it.petrillo.bonagatv.dao.UtenteLiveRepository;
import it.petrillo.bonagatv.models.UtenteLive;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import it.petrillo.bonagatv.models.dto.UtenteDto;
import it.petrillo.bonagatv.models.dto.UtenzeGratuiteDto;
import it.petrillo.bonagatv.utils.UtenzaPromoType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import it.petrillo.bonagatv.models.Evento;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    private UtenteLiveRepository utenteLiveRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private PasswordStandardRepository passwordStandardRepository;

    public void addUtenteWithCustomPsw(LoginRequest loginRequest, Long idEvento) {
        try {
            Optional<UtenteLive> utenteOp = utenteLiveRepository.getUtenteValidByEmail(loginRequest.getEmail());
            if (utenteOp.isPresent()) {
                throw new IllegalArgumentException();
            }
            UtenteLive utente = new UtenteLive();
            utente.setEvento(eventoRepository.findById(idEvento).orElseThrow());
            utente.setEmail(loginRequest.getEmail());
            if (loginRequest.getPassword() != null)
                utente.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
            else
                utente.setPassword(passwordStandardRepository.getPasswordStandardByIdEvento(idEvento));
            utenteLiveRepository.saveAndFlush(utente);
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
        Optional<UtenteLive> utenteOp = utenteLiveRepository.getUtenteValidByEmail(email);
        if (utenteOp.isEmpty()) {
            log.error("UtenteVod "+email+" non presente nel sistema");
            throw new UsernameNotFoundException("UtenteVod non presente nel DB");
        }

        UtenteLive utente = utenteOp.get();
        if (utente.getSessioneUtente() != null) {
            log.warn("Tentato il logout brute force di "+email+" ma l'utente non è collegato al sistema");
        }
        utente.setSessioneUtente(null);
        log.info("Forzato il logout per l'utente "+email);
        utenteLiveRepository.saveAndFlush(utente);

    }


    @Transactional
    public void addMassiveUsers(List<LoginRequest> utenti, Long idEvento) {
        try {
            List<UtenteLive> utentiDaAggiungere = new ArrayList<>();
            for (LoginRequest req : utenti) {
                UtenteLive u = new UtenteLive(req.getEmail().trim(), passwordStandardRepository.getPasswordStandardByIdEvento(idEvento));
                u.setEvento(eventoRepository.findById(idEvento).orElseThrow());
                utentiDaAggiungere.add(u);
            }
            utenteLiveRepository.saveAllAndFlush(utentiDaAggiungere);
            log.info("Aggiunti brute force "+utentiDaAggiungere.size()+" utenti");
        } catch (Exception e) {
            log.error("Errore nell'aggiunta massiva di utenti.", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<UtenteDto> getUtentiByEventIdAndEmailParziale(Long idEvento, String email) {
        return utenteLiveRepository.findUsersByEventId(idEvento, email);
    }

    public UtenzeGratuiteDto getUtenzeGratuiteByEventId(Long idEvento) {
        List<UtenteDto> utenzeBonaga = new ArrayList<>();
        List<UtenteDto> utenzeSegreteria = new ArrayList<>();
        List<UtenteDto> utenzeSponsor = new ArrayList<>();

        List<UtenteDto> tutteUtenzePromo = getUtentiByEventIdAndEmailParziale(idEvento, "@promo.com");

        for (UtenteDto u : tutteUtenzePromo) {
            if (u.getEmail().contains("bonaga"))
                utenzeBonaga.add(u);
            else if (u.getEmail().contains("sponsor"))
                utenzeSponsor.add(u);
            else if (u.getEmail().contains("segreteria"))
                utenzeSegreteria.add(u);
        }

        UtenzeGratuiteDto response = new UtenzeGratuiteDto();
        if (!utenzeBonaga.isEmpty())
            response.setUtenzeBonagaPromo(utenzeBonaga);

        if (!utenzeSegreteria.isEmpty())
            response.setUtenzeSegreteria(utenzeSegreteria);

        if (!utenzeSponsor.isEmpty())
            response.setUtenzeSponsor(utenzeSponsor);

        return response;
    }

    public UtenteDto addNewPromoUserByType(Long idEvento, UtenzaPromoType tipoUtenza) {
        UtenteLive nuovaUtenza = new UtenteLive();
        Evento eventoAssociato = eventoRepository.findById(idEvento).get();
        String defaultPsw = passwordStandardRepository.getPasswordStandardByIdEvento(idEvento);

        nuovaUtenza.setEvento(eventoAssociato);
        nuovaUtenza.setPassword(defaultPsw);
        nuovaUtenza.setEmail(generaNuovaMailDalTipo(idEvento, tipoUtenza));

        utenteLiveRepository.saveAndFlush(nuovaUtenza);

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Data di registrazione: " + now); // Log della data

        return new UtenteDto(nuovaUtenza.getId(),nuovaUtenza.getEmail(), defaultPsw, now);
    }

    private String generaNuovaMailDalTipo(Long idEvento, UtenzaPromoType tipoUtenza) {
        String prefix = tipoUtenza.name().toLowerCase();
        int newNumber = 1;
        Optional<List<String>> lastMails = utenteLiveRepository.getLastUtenzaPromoByIdEventoAndType(idEvento, prefix);
        if (lastMails.isPresent()) {
            List<String> mails = lastMails.get();
            if (!mails.isEmpty()) {
                String lastMail = mails.get(0);
                String[] emailParts = lastMail.split("@");
                String baseEmail = emailParts[0];
                String numberPart = baseEmail.replaceAll("[^0-9]", "");

                if (!numberPart.isEmpty()) {
                    newNumber = Integer.parseInt(numberPart) + 1;
                }
            }

        }

        String formattedNumber = String.format("%02d", newNumber);
        return tipoUtenza.name().toLowerCase() + formattedNumber + "@promo.com";

    }

    public List<Evento> getPPVEvents() {
        return eventoRepository.getPPVEvents();
    }
}
