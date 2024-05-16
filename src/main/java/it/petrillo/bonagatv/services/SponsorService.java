package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.SponsorRepository;
import it.petrillo.bonagatv.models.Sponsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    public List<Sponsor> getAllSponsor() {
        return sponsorRepository.findAll();
    }

}
