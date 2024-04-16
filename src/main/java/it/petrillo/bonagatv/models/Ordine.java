package it.petrillo.bonagatv.models;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento")
    private Date dataPagamento;

}