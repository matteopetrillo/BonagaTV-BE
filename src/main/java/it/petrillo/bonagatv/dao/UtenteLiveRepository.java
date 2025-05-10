package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.UtenteLive;
import it.petrillo.bonagatv.models.dto.UtenteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtenteLiveRepository extends JpaRepository<UtenteLive, Long> {

    @Query("SELECT u FROM UtenteLive u WHERE u.email = :email AND u.dataFineValidita IS NULL")
    Optional<UtenteLive> getLoggableByEmail(String email);
    @Query("SELECT u FROM UtenteLive u WHERE u.email = :email AND u.dataFineValidita IS NULL")
    Optional<UtenteLive> getUtenteValidByEmail(String email);
    Optional<UtenteLive> findBySessioneUtente(String sessioneUtente);

    @Query("SELECT new it.petrillo.bonagatv.models.dto.UtenteDto(u.id,u.email, u.password, o.dataPagamento) FROM UtenteLive u LEFT JOIN Ordine o ON u.id = o.utente.id WHERE u.evento.id = :idEvento AND u.email LIKE %:email%")
    List<UtenteDto> findUsersByEventId(Long idEvento, String email);

    @Query("SELECT u.email FROM UtenteLive u WHERE u.evento.id = :idEvento AND u.email LIKE :prefix% ORDER BY u.id DESC")
    Optional<List<String>> getLastUtenzaPromoByIdEventoAndType(Long idEvento, String prefix);
}