package bo.edu.ucb.currency.config;

import bo.edu.ucb.currency.dto.ErrorDto;
import bo.edu.ucb.currency.exception.CurrencyException;
import bo.edu.ucb.currency.exception.CurrencyServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(CurrencyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String,String> handleBadRequest(CurrencyException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(CurrencyServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorDto handleInternalServerError(CurrencyServiceException e) throws JsonProcessingException {
        return errorDto(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Map<String,String> handleInternalServerError(Exception e) {
        return Map.of("error", e.getMessage());
    }

    public ErrorDto errorDto(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, ErrorDto.class);
    }
}

