package it.petrillo.bonagatv.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CanaleDto {

    private Long id;
    private Long idEvento;
    private String nomeCanale;
    private String descCanale;
    private String nomeEvento;
    private String streamingSrc;
    private String logoEventoSrc;
    private String logoCanale;
    private Double costo;

}
