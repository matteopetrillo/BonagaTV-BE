package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.dto.UserRegistrationDto;
import it.petrillo.bonagatv.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/registra")
    public ResponseEntity<Long> registraUtente(@RequestBody UserRegistrationDto registrationDetails) {
        try {
            Long idUtente = utenteService.registraUtente(registrationDetails);
            return ResponseEntity.ok(idUtente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/elimina")
    public ResponseEntity<Void> eliminaUtente(@RequestParam Long id) {
        try {
            utenteService.eliminaUtente(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/check-by-email")
    public ResponseEntity<Long> checkEmailAvailable(@RequestParam String email) {
        try {
            Long idUtente = utenteService.isEmailAvailable(email);
            if (idUtente != 0L)
                return ResponseEntity.ok(idUtente);
            return ResponseEntity.ok(0L);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendCredentials(@RequestParam Long id, @RequestParam String lang) {
        try {
            utenteService.inoltraCredenziali(id, lang);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
