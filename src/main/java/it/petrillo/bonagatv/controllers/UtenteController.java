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
            return ResponseEntity.badRequest().body(0L);
        }
    }

    @DeleteMapping("/elimina")
    public ResponseEntity<Void> eliminaUtente(@RequestParam Long id) {
        try {
            utenteService.eliminaUtente(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/check-by-email")
    public ResponseEntity<Boolean> checkEmailAvailable(@RequestParam String email) {
        try {
            if (utenteService.isEmailAvailable(email))
                return ResponseEntity.ok(true);
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
