package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.CostoEvento;
import it.petrillo.bonagatv.models.TipoCanale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoCanaleRepository extends JpaRepository<TipoCanale, Long> {

}