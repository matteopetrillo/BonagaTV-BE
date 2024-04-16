package it.petrillo.bonagatv.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENTO")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_EVENTO", nullable = false)
    private Long id;

    @Column(name = "NOME", length = 1000)
    private String nome;

    @Column(name = "DATA_INIZIO")
    private LocalDate dataInizio;

    @Column(name = "DATA_FINE")
    private LocalDate dataFine;

    @Column(name = "LOGO_EVENTO_SRC", length = 4000)
    private String logoEvento;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_CANALE", nullable = false)
    private Canale canale;

}