package it.petrillo.bonagatv.dao;

import it.petrillo.bonagatv.models.PasswordStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PasswordStandardRepository extends JpaRepository<PasswordStandard, Long> {

    @Query("SELECT p.password FROM PasswordStandard p WHERE p.evento.id = :idEvento")
    String getPasswordStandardByIdEvento(Long idEvento);

}
