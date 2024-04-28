package it.petrillo.bonagatv.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdineDto {
    private String codiceOrdine;
    private String codicePagamento;
    private Double importo;
    private Long idUtente;
}