package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.config.security.CustomPasswordEncoder;
import it.petrillo.bonagatv.models.dto.LoginRequest;
import it.petrillo.bonagatv.services.AdminService;
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
    private AdminService service;

    @PostMapping("/add-utente")
    public ResponseEntity<Void> addUtenteBruteForce(@RequestBody LoginRequest loginData, @RequestParam Long evento) {
        try {
            service.addUtenteWithCustomPsw(loginData, evento);
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
            service.forceLogout(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/massive-add-utente")
    public ResponseEntity<Void> addMassiveUsersBruteForce(@RequestBody List<LoginRequest> utenti, @RequestParam Long evento) {
        try {
            service.addMassiveUsers(utenti, evento);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
