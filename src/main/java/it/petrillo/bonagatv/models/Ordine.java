package it.petrillo.bonagatv.models;

import lombok.Getter;
import it.petrillo.bonagatv.utils.TipoUtente;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private UtenteBase utente;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "codice_pagamento")
    private String codicePagamento;

    @Column(name = "tipo_utente", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;
}