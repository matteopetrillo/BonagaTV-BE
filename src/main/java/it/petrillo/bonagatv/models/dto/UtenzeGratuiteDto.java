package it.petrillo.bonagatv.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtenzeGratuiteDto {

    List<UtenteDto> utenzeSegreteria;
    List<UtenteDto> utenzeBonagaPromo;
    List<UtenteDto> utenzeSponsor;

}
