package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.dto.CreateOrderResponse;
import it.petrillo.bonagatv.models.dto.OrdineDto;
import it.petrillo.bonagatv.services.OrdineService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/ordine")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    @PostMapping("/crea-ordine")
    public ResponseEntity<CreateOrderResponse> creaOrdine(@RequestParam Long id) {
        try {
            CreateOrderResponse response = new CreateOrderResponse(ordineService.creaOrdine(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/conferma-ordine")
    public ResponseEntity<String> creaOrdine(@RequestParam String id) {
        try {
            String response = ordineService.confermaOrdine(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/registra-ordine")
    public ResponseEntity<Void> registraOrdine(@RequestBody OrdineDto body) {
        try {
            ordineService.registraOrdine(body);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
