package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.UtenteAbbonato;
import it.petrillo.bonagatv.models.dto.UtenteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtenteAbbonatoRepository extends JpaRepository<UtenteAbbonato, Long> {

    @Query("SELECT u FROM UtenteAbbonato u WHERE u.email = :email AND u.dataFineValidita IS NULL")
    Optional<UtenteAbbonato> getLoggableByEmail(String email);
    @Query("SELECT u FROM UtenteAbbonato u WHERE u.email = :email AND u.dataFineValidita IS NULL")
    Optional<UtenteAbbonato> getUtenteValidByEmail(String email);
    Optional<UtenteAbbonato> findBySessioneUtente(String sessioneUtente);

    @Query("SELECT new it.petrillo.bonagatv.models.dto.UtenteDto(u.email, u.password, o.dataPagamento) FROM UtenteAbbonato u LEFT JOIN Ordine o ON u.id = o.utenteAbbonato.id WHERE u.evento.id = :idEvento AND u.email LIKE %:email%")
    List<UtenteDto> findUsersByEventId(Long idEvento, String email);

    @Query("SELECT u.email FROM UtenteAbbonato u WHERE u.evento.id = :idEvento AND u.email LIKE :prefix% ORDER BY u.id DESC")
    Optional<List<String>> getLastUtenzaPromoByIdEventoAndType(Long idEvento, String prefix);
}