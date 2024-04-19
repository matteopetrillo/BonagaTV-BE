package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.UtenteAbbonatoRepository;
import it.petrillo.bonagatv.models.UtenteAbbonato;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteAbbonatoRepository utenteRepository;

    @Transactional
    public void attivaUtente(Long id) {
        utenteRepository.attivaUtenteById(id);
        log.info("L'utente "+id+" è stato attivato");
    }

    @Transactional
    public void disattivaUtente(Long id) {
        utenteRepository.disattivaUtenteById(id);
        log.info("L'utente "+id+" è stato disattivato");
    }

}
