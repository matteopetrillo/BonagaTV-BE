package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.UtenteAbbonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtenteAbbonatoRepository extends JpaRepository<UtenteAbbonato, Long> {

    @Query("SELECT u FROM UtenteAbbonato u WHERE u.email = :email AND u.password = :password AND u.idEvento.id = :idEvento" +
            " AND (u.dataFineValidita > CURRENT_DATE OR u.dataFineValidita IS NULL)")
    Optional<UtenteAbbonato> getByCredentialsAndEvent(String email, String password, Long idEvento);

    @Query("SELECT u FROM UtenteAbbonato u WHERE u.email = :email AND u.dataFineValidita IS NULL AND (u.isActive = 0 OR u.isActive IS NULL)")
    Optional<UtenteAbbonato> getValidByEmail(String email);

    @Modifying
    @Query("UPDATE UtenteAbbonato u set u.isActive = 1 WHERE u.id = :id")
    void attivaUtenteById(Long id);

    @Modifying
    @Query("UPDATE UtenteAbbonato u set u.isActive = 0 WHERE u.id = :id")
    void disattivaUtenteById(Long id);

}