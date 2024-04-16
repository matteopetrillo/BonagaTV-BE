package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.Canale;
import it.petrillo.bonagatv.models.dto.RaccoltaCanali;
import it.petrillo.bonagatv.services.CanaleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/canale")
public class CanaleController {

    @Autowired
    private CanaleService canaleService;

    @GetMapping("/raccolta")
    public ResponseEntity<RaccoltaCanali> getCanali() {
        RaccoltaCanali canali = canaleService.getRaccoltaCanaliGratuiti();
        return ResponseEntity.ok(canali);
    }

    @GetMapping("/getInfo/{id}")
    public ResponseEntity<Canale> getInfoCanale(@PathVariable Long id) {
        Canale canale = canaleService.getInfoCanale(id);
        return ResponseEntity.ok(canale);
    }


}
