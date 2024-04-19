package it.petrillo.bonagatv.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ASSOCIAZIONE")
public class Associazione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_ASSOCIAZIONE", nullable = false)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ABBREVIAZIONE")
    private String abbreviazione;

    @Column(name = "LOGO_SRC")
    private String logoSrc;

}