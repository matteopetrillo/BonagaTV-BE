package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.CanaleRepository;
import it.petrillo.bonagatv.dao.EventoRepository;
import it.petrillo.bonagatv.models.Canale;
import it.petrillo.bonagatv.models.Evento;
import it.petrillo.bonagatv.models.dto.EventoDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CanaleRepository canaleRepository;

    public List<EventoDto> getAllEventi(Integer month, Integer year) {
        LocalDate dataInizio = LocalDate.of(year, month, 1);
        List<Evento> eventi = eventoRepository.getAllEventi(dataInizio);
        return eventi.stream().map(e -> EventoDto.builder()
                .id(e.getId())
                .nomeEvento(e.getNome())
                .dataInizio(e.getDataInizio())
                .dataFine(e.getDataFine())
                .idCanale(e.getCanale().getId())
                .nomeCanale(e.getCanale().getAssociazione().getAbbreviazione())
                .logoEvento(e.getLogoEvento())
                .build()).toList();
    }

    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public EventoDto createEvento(EventoDto eventoDto) {
        Evento evento = new Evento();
        evento.setNome(eventoDto.getNomeEvento());
        evento.setDataInizio(eventoDto.getDataInizio());
        evento.setDataFine(eventoDto.getDataFine());
        evento.setLogoEvento(eventoDto.getLogoEvento());
        Canale canale = canaleRepository.findById(eventoDto.getIdCanale()).orElseThrow(() -> new RuntimeException("Canale not found"));
        evento.setCanale(canale);
        Evento savedEvento = eventoRepository.save(evento);
        return EventoDto.builder()
                .id(savedEvento.getId())
                .nomeEvento(savedEvento.getNome())
                .dataInizio(savedEvento.getDataInizio())
                .dataFine(savedEvento.getDataFine())
                .idCanale(savedEvento.getCanale().getId())
                .nomeCanale(savedEvento.getCanale().getAssociazione().getAbbreviazione())
                .logoEvento(savedEvento.getLogoEvento())
                .build();
    }
}
