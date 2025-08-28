package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.Evento;
import it.petrillo.bonagatv.models.dto.EventoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    @Query("SELECT e FROM Evento e WHERE e.canale.tipoCanale.id = 2")
    List<Evento> getPPVEvents();

    @Query("SELECT e FROM Evento e WHERE e.dataInizio >= :dataInizio ORDER BY e.dataInizio ASC")
    List<Evento> getAllEventi(LocalDate dataInizio);
}