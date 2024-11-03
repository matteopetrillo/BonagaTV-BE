package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    @Query("SELECT e FROM Evento e WHERE e.canale.tipoCanale.id = 2")
    List<Evento> getPPVEvents();
}