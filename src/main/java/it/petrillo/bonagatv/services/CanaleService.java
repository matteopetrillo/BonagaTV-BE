package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.CanaleRepository;
import it.petrillo.bonagatv.models.Canale;
import it.petrillo.bonagatv.models.Evento;
import it.petrillo.bonagatv.models.dto.CanaleDto;
import it.petrillo.bonagatv.models.dto.RaccoltaCanali;
import it.petrillo.bonagatv.models.mappers.CanaleMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class CanaleService {

    @Autowired
    private CanaleRepository canaleRepository;

    @Autowired
    private UtenteService utenteService;

    public RaccoltaCanali getRaccoltaCanali() {
        List<CanaleDto> canaliProxLive = new ArrayList<>();
        List<CanaleDto> canaliOffline = new ArrayList<>();
        CanaleDto specialEventDto = null;
        try {
            List<Canale> canaliGratuiti = canaleRepository.getCanaliGratuiti();
            Canale specialEvent = canaleRepository.getCanalePagamento();

            for (Canale c : canaliGratuiti) {
                CanaleDto dto = Mappers.getMapper(CanaleMapper.class).toDto(c);
                HashMap<String, String> infoNextEvento = getWeekEvents(c.getEventi(), ChannelType.GRATUITO);
                if (infoNextEvento.containsKey("nomeEvento") && infoNextEvento.containsKey("logoEvento")
                        && infoNextEvento.containsKey("idEvento")) {
                    dto.setNomeEvento(infoNextEvento.get("nomeEvento"));
                    dto.setLogoEventoSrc(infoNextEvento.get("logoEvento"));
                    dto.setIdEvento(Long.valueOf(infoNextEvento.get("idEvento")));
                    canaliProxLive.add(dto);
                } else {
                    canaliOffline.add(dto);
                }
            }

            HashMap<String, String> infoNextEvento = getWeekEvents(specialEvent.getEventi(), ChannelType.PAGAMENTO);
            if (infoNextEvento.containsKey("nomeEvento") && infoNextEvento.containsKey("logoEvento")
                    && infoNextEvento.containsKey("idEvento")) {
                specialEventDto = Mappers.getMapper(CanaleMapper.class).toDto(specialEvent);
                specialEventDto.setNomeEvento(infoNextEvento.get("nomeEvento"));
                specialEventDto.setLogoEventoSrc(infoNextEvento.get("logoEvento"));
                specialEventDto.setIdEvento(Long.valueOf(infoNextEvento.get("idEvento")));
                specialEventDto.setStreamingSrc(null);
                specialEventDto.setCosto(Double.valueOf(infoNextEvento.getOrDefault("costo",null)));
            }

        } catch (Exception e) {
            log.error("Errore nel metodo getRaccoltaCanali "+e.getMessage());
            e.printStackTrace();
        }

        return new RaccoltaCanali(canaliProxLive, canaliOffline, specialEventDto);
    }

    public Canale getInfoCanale(Long id) {
        log.info("Chiamato il metodo getInfoCanale con id: "+id);
        Optional<Canale> canaleOptional = canaleRepository.findById(id);
        log.info("Le info sono state recuperate: "+canaleOptional.isPresent());
        return canaleOptional.orElse(null);
    }

    private HashMap<String,String> getWeekEvents(List<Evento> eventi, ChannelType type) {
        HashMap<String,String> nextEvento = new HashMap<>();

        LocalDate oggi = LocalDate.now();
        LocalDate beginTarget = null;
        LocalDate endTarget = null;

        if (type.equals(ChannelType.GRATUITO)) {
            beginTarget = oggi.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            endTarget = beginTarget.plusWeeks(1);
        } else {
            beginTarget = oggi;
            endTarget = oggi.plusWeeks(2);
        }

        for (Evento e : eventi) {
            if ((e.getDataInizio().isAfter(beginTarget) || e.getDataInizio().isEqual(beginTarget)) &&
                    (e.getDataFine().isBefore(endTarget)) ||
                    ((e.getDataInizio().isBefore(beginTarget) && e.getDataFine().isAfter(beginTarget)))) {
                nextEvento.put("nomeEvento", e.getNome());
                nextEvento.put("logoEvento", e.getLogoEvento());
                nextEvento.put("idEvento", String.valueOf(e.getId()));
                if (e.getCostoEvento() != null)
                    nextEvento.put("costo", String.valueOf(e.getCostoEvento().getImporto()));
                break;
            }
        }

        return nextEvento;
    }


    public Canale getSpecialEventChannel() {
        return canaleRepository.getCanalePagamento();
    }
}
