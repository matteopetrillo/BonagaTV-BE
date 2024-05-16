package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}