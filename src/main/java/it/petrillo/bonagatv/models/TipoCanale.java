package it.petrillo.bonagatv.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TIPO_CANALE")
public class TipoCanale {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_TIPO_CANALE", nullable = false)
    private Long id;

    @Column(name = "TIPO")
    private String tipo;

}