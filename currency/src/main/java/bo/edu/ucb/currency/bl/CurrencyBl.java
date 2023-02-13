package bo.edu.ucb.currency.bl;

import bo.edu.ucb.currency.dto.ResponseDto;
import bo.edu.ucb.currency.exception.CurrencyException;
import bo.edu.ucb.currency.exception.CurrencyServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class CurrencyBl {
    @Value("${currency.url}")
    private String url;
    @Value("${currency.api_key}")
    private String api_key;

    private static Logger logger = LoggerFactory.getLogger(CurrencyBl.class.getName());

    public ResponseDto currency(String to, String from, BigDecimal amount) throws CurrencyException, CurrencyServiceException, IOException {
        logger.info("Starting the Business Logic layer");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            logger.error("Amount must be greater than 0");
            throw new CurrencyException("Amount must be greater than 0");
        }
        logger.info("Starting the external service call");
        ResponseDto responseDto = currencyService(to, from, amount);
        logger.info("Finishing the external service call");
        logger.info("Finishing the Business Logic layer");
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
            String stringResponse = response.body().string();
            if (response.isSuccessful()) {
                logger.info("Successful external service call");
                return responseDto(stringResponse);
            }
            logger.error("Error calling the external service");
            logger.error(stringResponse);
            throw new CurrencyServiceException(stringResponse);
        } catch (IOException e) {
            logger.error("Error calling the external service");
            logger.info(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public ResponseDto responseDto(String response) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, ResponseDto.class);
    }
}
