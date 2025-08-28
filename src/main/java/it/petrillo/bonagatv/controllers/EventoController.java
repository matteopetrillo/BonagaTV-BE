package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.dto.EventoDto;
import it.petrillo.bonagatv.services.CanaleService;
import it.petrillo.bonagatv.services.EventoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/getAll")
    public ResponseEntity<List<EventoDto>> getAllEventi(@RequestParam Integer month, @RequestParam Integer year) {
        List<EventoDto> eventi = eventoService.getAllEventi(month, year);
        return ResponseEntity.ok(eventi);
    }

    @PostMapping("/create")
    public ResponseEntity<EventoDto> createEvento(@RequestBody EventoDto eventoDto) {
        try {
            EventoDto createdEvento = eventoService.createEvento(eventoDto);
            return ResponseEntity.ok(createdEvento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        try {
            eventoService.deleteEvento(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
