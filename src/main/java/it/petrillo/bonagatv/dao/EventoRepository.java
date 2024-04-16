package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}