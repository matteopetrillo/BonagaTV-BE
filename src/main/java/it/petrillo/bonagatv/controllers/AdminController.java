package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.Evento;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import it.petrillo.bonagatv.models.dto.UtenteDto;
import it.petrillo.bonagatv.models.dto.UtenzeGratuiteDto;
import it.petrillo.bonagatv.services.AdminService;
import it.petrillo.bonagatv.services.UtenteService;
import it.petrillo.bonagatv.utils.UtenzaPromoType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-utente")
    public ResponseEntity<Void> addUtenteBruteForce(@RequestBody LoginRequest loginData, @RequestParam Long evento) {
        try {
            adminService.addUtenteWithCustomPsw(loginData, evento);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/force-logout")
    public ResponseEntity<Void> forceLogout(@RequestParam("email") String email) {
        try {
            adminService.forceLogout(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/massive-add-utente")
    public ResponseEntity<Void> addMassiveUsersBruteForce(@RequestBody List<LoginRequest> utenti, @RequestParam Long evento) {
        try {
            adminService.addMassiveUsers(utenti, evento);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllUsers/{id-evento}")
    public ResponseEntity<List<UtenteDto>> getUsersByEventId(@PathVariable("id-evento") Long idEvento, @RequestParam("email") String email) {
        List<UtenteDto> utenti = adminService.getUtentiByEventIdAndEmailParziale(idEvento, email);
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/getUtenzeGratuite/{id-evento}")
    public ResponseEntity<UtenzeGratuiteDto> getUtenzeGratuiteByEventId(@PathVariable("id-evento") Long idEvento) {
        UtenzeGratuiteDto response = adminService.getUtenzeGratuiteByEventId(idEvento);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addPromoUser/{id-evento}")
    public ResponseEntity<UtenteDto> addNewPromoUserByType(@PathVariable("id-evento") Long idEvento, @RequestParam("utenza") UtenzaPromoType tipoUtenza) {
        UtenteDto nuovaUtenza = adminService.addNewPromoUserByType(idEvento,tipoUtenza);
        return ResponseEntity.ok(nuovaUtenza);
    }

    @GetMapping("/getPPVEvents")
    public ResponseEntity<List<Evento>> getPPVEvents() {
        List<Evento> eventi = adminService.getPPVEvents();
        return ResponseEntity.ok(eventi);
    }

}
