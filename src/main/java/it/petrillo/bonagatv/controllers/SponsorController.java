package it.petrillo.bonagatv.controllers;

import it.petrillo.bonagatv.models.Sponsor;
import it.petrillo.bonagatv.services.SponsorService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/sponsor")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Sponsor>> getAllSponsors() {
        try {
            return ResponseEntity.ok(sponsorService.getAllSponsor());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
