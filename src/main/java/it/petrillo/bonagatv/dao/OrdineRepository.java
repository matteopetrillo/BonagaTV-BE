package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
}