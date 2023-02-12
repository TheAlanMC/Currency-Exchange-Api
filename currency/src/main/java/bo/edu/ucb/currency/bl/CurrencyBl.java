package bo.edu.ucb.currency.bl;

import bo.edu.ucb.currency.dto.ResponseDto;
import bo.edu.ucb.currency.exception.CurrencyException;
import bo.edu.ucb.currency.exception.CurrencyServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
public class CurrencyBl {
    @Value("${currency.url}")
    private String url;
    @Value("${currency.api_key}")
    private String api_key;

    private static Logger logger = Logger.getLogger(CurrencyBl.class.getName());

    public ResponseDto currency(String to, String from, BigDecimal amount) throws CurrencyException, CurrencyServiceException, IOException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            logger.warning("El monto debe ser mayor a 0");
            throw new CurrencyException("El monto debe ser mayor a 0");
        }
        logger.info("Iniciando la llamada al servicio externo");
        ResponseDto responseDto = currencyService(to, from, amount);
        logger.info("Finalizando la llamada al servicio externo");
        return responseDto;
    }

    public ResponseDto currencyService(String to, String from, BigDecimal amount) throws CurrencyServiceException, IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url+"?from="+from+"&to="+to+"&amount="+amount)
                .addHeader("apiKey", api_key)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return responseDto(response.body().string());
            }
            String error = response.body().string();
            logger.warning("Error al consumir el servicio externo");
            logger.info(error);
            throw new CurrencyServiceException(error);
        } catch (IOException e) {
            logger.warning("Error al consumir el servicio externo");
            logger.info(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public ResponseDto responseDto(String response) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, ResponseDto.class);
    }
}
