package it.petrillo.bonagatv.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utente_vod")
@Getter
@Setter
@NoArgsConstructor
public class UtenteVod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "data_registrazione", nullable = false, updatable = false)
    private LocalDateTime dataRegistrazione;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "cognome", length = 50)
    private String cognome;

}