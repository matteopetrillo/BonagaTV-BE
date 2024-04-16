package it.petrillo.bonagatv.services;

import it.petrillo.bonagatv.dao.CanaleRepository;
import it.petrillo.bonagatv.models.Canale;
import it.petrillo.bonagatv.models.Evento;
import it.petrillo.bonagatv.models.dto.CanaleDto;
import it.petrillo.bonagatv.models.dto.RaccoltaCanali;
import it.petrillo.bonagatv.models.mappers.CanaleMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class CanaleService {

    @Autowired
    private CanaleRepository canaleRepository;

    @Transactional
    public RaccoltaCanali getRaccoltaCanaliGratuiti() {
        log.info("Richieste le raccolte dei canali gratuiti");
        List<CanaleDto> canaliProxLive = new ArrayList<>();
        List<CanaleDto> canaliOffline = new ArrayList<>();
        try {

            List<Canale> canali = canaleRepository.getCanaliGratuiti();
            for (Canale c : canali) {

                CanaleDto dto = Mappers.getMapper(CanaleMapper.class).toDto(c);

                if (c.getEventi() == null || c.getEventi().isEmpty()) {
                    canaliOffline.add(dto);
                    continue;
                }

                boolean toAddProxLive = false;
                String nomeEvento = null;
                String logoEvento = null;

                for (Evento e : c.getEventi()) {
                    LocalDate oggi = LocalDate.now();
                    LocalDate giornoTarget = oggi.plusDays(7);

                    if ((e.getDataInizio().isBefore(giornoTarget) && (e.getDataInizio().isEqual(oggi) || e.getDataInizio().isAfter(oggi))) ||
                            (e.getDataFine().isAfter(LocalDate.now()) || e.getDataFine().isEqual(LocalDate.now()))) {
                        toAddProxLive = true;
                        nomeEvento = e.getNome();
                        logoEvento = e.getLogoEvento();
                        break;
                    }
                }

                if (toAddProxLive) {
                    dto.setNomeEvento(nomeEvento);
                    dto.setLogoEventoSrc(logoEvento);
                    canaliProxLive.add(dto);
                } else {
                    canaliOffline.add(dto);
                }
            }

        } catch (Exception e) {
            log.error("Errore nel metodo getRaccoltaCanaliGratuiti "+e.getMessage());
            e.printStackTrace();
        }
        log.info("Trovati: "+canaliProxLive.size()+" canali prossimamente live e "+canaliOffline.size()+" canali offline");
        return new RaccoltaCanali(canaliProxLive, canaliOffline);
    }

    public Canale getInfoCanale(Long id) {
        log.info("Chiamato il metodo getInfoCanale con id: "+id);
        Optional<Canale> canaleOptional = canaleRepository.findById(id);
        log.info("Le info sono state recuperate: "+canaleOptional.isPresent());
        return canaleOptional.orElse(null);
    }
}
