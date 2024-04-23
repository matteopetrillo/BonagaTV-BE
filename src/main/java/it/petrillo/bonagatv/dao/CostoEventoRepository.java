package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.CostoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CostoEventoRepository extends JpaRepository<CostoEvento, Long> {

    @Query("SELECT ce.importo FROM CostoEvento ce where ce.evento.id = :eventoId")
    Double findByEventoId(Long eventoId);
}