package it.petrillo.bonagatv.models;

import it.petrillo.bonagatv.models.Evento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @ManyToOne()
    @JoinColumn(name = "id_evento")
    private Evento idEvento;

    @Column(name = "email", length = 500, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active")
    private Long isActive;

    @Column(name = "data_fine_validita")
    private LocalDate dataFineValidita;


    public UtenteAbbonato(String email,String password) {
        this.email = email;
        this.password = password;
    }
}