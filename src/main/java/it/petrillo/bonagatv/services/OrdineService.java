package it.petrillo.bonagatv.services;


import it.petrillo.bonagatv.dao.CostoEventoRepository;
import it.petrillo.bonagatv.dao.TipoCanaleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrdineService {

    @Autowired
    private PaypalApiClient paypalClient;
    @Autowired
    private CostoEventoRepository costoEventoRepository;

    public String creaOrdine(Long eventoId) {

        log.info("Creazione di un ordine per l'evento con id "+eventoId);

        Double importo = costoEventoRepository.findByEventoId(eventoId);

        try {
            String response = paypalClient.createOrder(importo);
            log.info("Completata la creazione dell'ordine "+response);
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

        log.info("Completamento dell'ordine "+idOrdine);

        try {
            String id = paypalClient.completeOrder(idOrdine);
            log.info("Ordine "+id+" completato con successo.");
            return id;
        } catch (JSONException e) {
            log.error("Errore nel parsing JSON in OrdineService.creaOrdine", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Errore in OrdineService.creaOrdine", e);
            throw new RuntimeException(e);
        }

    }



}
