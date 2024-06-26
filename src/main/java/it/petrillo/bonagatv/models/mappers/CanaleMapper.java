package it.petrillo.bonagatv.models.mappers;

import it.petrillo.bonagatv.models.Canale;
import it.petrillo.bonagatv.models.dto.CanaleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CanaleMapper {

    @Mapping(source = "associazione.abbreviazione", target = "nomeCanale")
    CanaleDto toDto(Canale canale);

}
