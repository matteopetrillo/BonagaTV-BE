package it.petrillo.bonagatv.models.mappers;

import it.petrillo.bonagatv.models.Ordine;
import it.petrillo.bonagatv.models.dto.OrdineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrdineMapper {
    @Mapping(source = "codiceOrdine", target = "codiceOrdine")
    @Mapping(source = "importo", target = "importo")
    @Mapping(source = "codicePagamento", target = "codicePagamento")
    Ordine toEntity(OrdineDto canale);
}
