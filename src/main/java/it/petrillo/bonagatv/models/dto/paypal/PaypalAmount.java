package it.petrillo.bonagatv.models.dto.paypal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaypalAmount {
    private String currency_code = "EUR";
    private Double value;
    public PaypalAmount(Double importo) {
        this.value = importo;
    }
}
