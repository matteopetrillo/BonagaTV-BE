package it.petrillo.bonagatv.services;



import it.petrillo.bonagatv.models.dto.paypal.CreateOrderPayload;
import it.petrillo.bonagatv.models.dto.paypal.PaypalItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaypalApiClient {

    @Value("${paypal.clientid}")
    private String CLIENT_ID;
    @Value("${paypal.clientsecret}")
    private String CLIENT_SECRET;
    private final RestTemplate client = new RestTemplate();
    @Value("${paypal.baseurl}")
    private String BASE_URL;
    private final String GET_TOKEN_PATH = "/v1/oauth2/token";
    private final String CREATE_ORDER_PATH = "/v2/checkout/orders/";


    private String getAccessToken() throws JSONException {

        String credentials = CLIENT_ID+":"+CLIENT_SECRET;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(Base64.getEncoder().encodeToString(credentials.getBytes()));

        HttpEntity<String> body = new HttpEntity<>("grant_type=client_credentials",headers);

        ResponseEntity<String> response = client.postForEntity(BASE_URL+GET_TOKEN_PATH,body,String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        return (String) jsonObject.get("access_token");

    }

    public String createOrder(Double importo) throws JSONException {

        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        CreateOrderPayload payload = new CreateOrderPayload();
        payload.getPurchase_units().add(new PaypalItem(importo));

        HttpEntity<CreateOrderPayload> body = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = client.postForEntity(BASE_URL+CREATE_ORDER_PATH,body,String.class);


        JSONObject jsonObject = new JSONObject(response.getBody());

        return (String) jsonObject.get("id");

    }

    public String completeOrder(String orderId) throws JSONException {

        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<String> body = new HttpEntity<>("",headers);

        ResponseEntity<String> response = client.postForEntity(BASE_URL+CREATE_ORDER_PATH+orderId+"/capture",body,String.class);

        if (response.getStatusCodeValue() == 201)
            return response.getBody();
        else
            throw new RuntimeException("Non è stato possibile completare l'ordine");
    }

}
