package it.petrillo.bonagatv.models.dto;

import it.petrillo.bonagatv.models.Canale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RaccoltaCanali {

    private List<CanaleDto> canaliProssimamenteLive;
    private List<CanaleDto> canaliOffline;
    private CanaleDto specialEvent;

}
