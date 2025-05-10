package it.petrillo.bonagatv.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "libreria_utente_vod")
@Getter
@Setter
@NoArgsConstructor
public class LibreriaUtenteVod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_vod_id", nullable = false)
    private UtenteVod utenteVod;

    @ManyToOne
    @JoinColumn(name = "vod_id", nullable = false)
    private Vod vod;

    @Column(name = "data_acquisto", nullable = false)
    private LocalDateTime dataAcquisto;

    @Column(name = "prezzo_acquisto", nullable = false)
    private Double prezzoAcquisto;
}