package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.UtenteAbbonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UtenteAbbonatoRepository extends JpaRepository<UtenteAbbonato, Long> {

    @Query("SELECT u FROM UtenteAbbonato u WHERE u.email = :email AND u.sessioneUtente IS NULL AND u.dataFineValidita IS NULL")
    Optional<UtenteAbbonato> getValidByEmail(String email);

    Optional<UtenteAbbonato> findBySessioneUtente(String sessioneUtente);


}