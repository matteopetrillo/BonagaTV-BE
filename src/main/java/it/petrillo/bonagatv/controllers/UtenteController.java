package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/attiva")
    public ResponseEntity<Void> attivaUtente(@RequestParam Long id) {
        utenteService.attivaUtente(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disattiva")
    public ResponseEntity<Void> disattivaUtente(@RequestParam Long id) {
        utenteService.disattivaUtente(id);
        return ResponseEntity.ok().build();
    }

}
