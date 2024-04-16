package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.UtenteAbbonato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteAbbonatoRepository extends JpaRepository<UtenteAbbonato, Long> {
}