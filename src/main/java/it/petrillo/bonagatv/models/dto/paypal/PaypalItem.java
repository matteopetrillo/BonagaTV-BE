package it.petrillo.bonagatv.models.dto.paypal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaypalItem {

    private PaypalAmount amount;

    public PaypalItem(Double importo) {
        this.amount = new PaypalAmount(importo);
    }
}
