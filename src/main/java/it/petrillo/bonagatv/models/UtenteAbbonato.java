package it.petrillo.bonagatv.models;

import it.petrillo.bonagatv.models.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utente_abbonato")
public class UtenteAbbonato {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento idEvento;

    @Column(name = "email", length = 500, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fine_validita", nullable = false)
    private Date dataFineValidita;

}