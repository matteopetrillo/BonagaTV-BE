package it.petrillo.bonagatv.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CANALE")
public class Canale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_CANALE", nullable = false)
    private Long id;

    @Column(name = "STREAMING_SRC",  length = 4000, nullable = false)
    private String streamingSrc;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_canale", nullable = false)
    private TipoCanale tipoCanale;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "ID_ASSOCIAZIONE")
    private Associazione associazione;

    @OneToMany(mappedBy = "canale", orphanRemoval = true)
    private List<Evento> eventi = new ArrayList<>();

}