package bo.edu.ucb.currency.api;

import bo.edu.ucb.currency.bl.CurrencyBl;
import bo.edu.ucb.currency.dto.ResponseDto;
import bo.edu.ucb.currency.exception.CurrencyException;
import bo.edu.ucb.currency.exception.CurrencyServiceException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/api/currency")
public class CurrencyApi {

    private static Logger logger = Logger.getLogger(CurrencyApi.class.getName());
    private final CurrencyBl currencyBl;

    public CurrencyApi(CurrencyBl currencyBl) {
        this.currencyBl = currencyBl;
    }

@GetMapping()
    public ResponseDto currency(@RequestParam String to, @RequestParam String from, @RequestParam BigDecimal amount) throws CurrencyException, CurrencyServiceException, IOException {
    logger.info("Iniciando la llamada a la API");
    ResponseDto result = currencyBl.currency(to,from,amount);
    logger.info(result.toString());
    logger.info("Finalizando la llamada a la API");
    return result;
    }
}
