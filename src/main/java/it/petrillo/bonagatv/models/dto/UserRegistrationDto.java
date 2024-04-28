package it.petrillo.bonagatv.models.dto;

import it.petrillo.bonagatv.models.Evento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    private String email;
    private Long idEvento;

}
