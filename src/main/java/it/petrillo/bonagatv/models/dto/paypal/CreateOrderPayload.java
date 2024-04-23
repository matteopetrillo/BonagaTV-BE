package it.petrillo.bonagatv.models.dto.paypal;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderPayload {

    private String intent = "CAPTURE";
    private List<PaypalItem> purchase_units = new ArrayList<>();

}
