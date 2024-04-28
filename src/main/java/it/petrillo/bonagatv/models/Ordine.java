package it.petrillo.bonagatv.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codice_ordine", length = 1000, nullable = false)
    private String codiceOrdine;

    @Column(name = "importo")
    private Double importo;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "id_utente", nullable = false)
    private UtenteAbbonato utenteAbbonato;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "codice_pagamento")
    private String codicePagamento;

}