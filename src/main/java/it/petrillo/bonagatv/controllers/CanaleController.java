package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.Canale;
import it.petrillo.bonagatv.models.dto.CanaleDto;
import it.petrillo.bonagatv.models.dto.RaccoltaCanali;
import it.petrillo.bonagatv.services.CanaleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/canale")
public class CanaleController {

    @Autowired
    private CanaleService canaleService;

    @GetMapping("/raccolta")
    public ResponseEntity<RaccoltaCanali> getCanali() {
        RaccoltaCanali canali = canaleService.getRaccoltaCanali();
        return ResponseEntity.ok(canali);
    }

    @GetMapping("/getInfo/{id}")
    public ResponseEntity<Canale> getInfoCanale(@PathVariable Long id) {
        Canale canale = canaleService.getInfoCanale(id);
        return ResponseEntity.ok(canale);
    }

    @GetMapping("/getSpecial")
    public ResponseEntity<Canale> getSpecialEventChannel() {
        Canale canale = canaleService.getSpecialEventChannel();
        return ResponseEntity.ok(canale);
    }

    @GetMapping("/getAllFreeChannels")
    public ResponseEntity<List<CanaleDto>> getAllFreeChannels() {
        List<CanaleDto> canali = canaleService.getAllFreeChannels();
        return ResponseEntity.ok(canali);
    }


}
