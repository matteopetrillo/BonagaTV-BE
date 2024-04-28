package it.petrillo.bonagatv.services;


import it.petrillo.bonagatv.dao.CostoEventoRepository;
import it.petrillo.bonagatv.dao.OrdineRepository;
import it.petrillo.bonagatv.dao.TipoCanaleRepository;
import it.petrillo.bonagatv.dao.UtenteAbbonatoRepository;
import it.petrillo.bonagatv.models.Ordine;
import it.petrillo.bonagatv.models.UtenteAbbonato;
import it.petrillo.bonagatv.models.dto.OrdineDto;
import it.petrillo.bonagatv.models.mappers.OrdineMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private PaypalApiClient paypalClient;
    @Autowired
    private CostoEventoRepository costoEventoRepository;
    @Autowired
    private UtenteAbbonatoRepository utenteAbbonatoRepository;

    public String creaOrdine(Long eventoId) {

        Double importo = costoEventoRepository.findByEventoId(eventoId);

        try {
            String response = paypalClient.createOrder(importo);
            log.info("Creazione dell'ordine "+response);
            return response;
        } catch (JSONException e) {
            log.error("Errore nel parsing JSON in OrdineService.creaOrdine", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Errore in OrdineService.creaOrdine", e);
            throw new RuntimeException(e);
        }

    }

    public String confermaOrdine(String idOrdine) {

        try {
            String response = paypalClient.completeOrder(idOrdine);
            JSONObject jsonResponse = new JSONObject(response);
            log.info("Ordine "+jsonResponse.get("id")+" completato con successo.");
            return response;
        } catch (JSONException e) {
            log.error("Errore nel parsing JSON in OrdineService.creaOrdine", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Errore in OrdineService.creaOrdine", e);
            throw new RuntimeException(e);
        }

    }


    public void registraOrdine(OrdineDto body) {

        try {
            Ordine nuovoOrdine = Mappers.getMapper(OrdineMapper.class).toEntity(body);
            nuovoOrdine.setDataPagamento(LocalDateTime.now());
            Optional<UtenteAbbonato> utente = utenteAbbonatoRepository.findById(body.getIdUtente());
            nuovoOrdine.setUtenteAbbonato(utente.orElseThrow());
            ordineRepository.saveAndFlush(nuovoOrdine);
            log.info("Registrato l'ordine con codice "+nuovoOrdine.getCodiceOrdine()+" dell'utente "+nuovoOrdine.getUtenteAbbonato().getEmail()
            +" per un importo totale di €"+nuovoOrdine.getImporto());
        } catch (Exception e) {
            log.error("Error in registraOrdine");
            throw new RuntimeException();
        }

    }
}
