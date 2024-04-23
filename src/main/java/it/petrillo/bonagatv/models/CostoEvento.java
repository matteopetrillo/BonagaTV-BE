package it.petrillo.bonagatv.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "costo_evento")
public class CostoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "importo")
    private Double importo;

}