package it.petrillo.bonagatv.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utente_live")
public class UtenteLive extends UtenteBase{

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "data_fine_validita")
    private LocalDate dataFineValidita;

    @Column(name = "id_sessione_utente", unique = true)
    private String sessioneUtente;

    public UtenteLive(String email, String password) {
        super();
        setEmail(email);
        setPassword(password);
    }
}