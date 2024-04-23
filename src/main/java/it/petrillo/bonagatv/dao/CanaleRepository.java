package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.Canale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CanaleRepository extends JpaRepository<Canale, Long> {
    @Query("SELECT c FROM Canale c WHERE c.tipoCanale.id = 1")
    List<Canale> getCanaliGratuiti();

    @Query("SELECT c FROM Canale c WHERE c.id = 8")
    Canale getCanalePagamento();

}