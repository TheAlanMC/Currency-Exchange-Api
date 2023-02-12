package bo.edu.ucb.currencykt.api

import bo.edu.ucb.currencykt.bl.CurrencyBl
import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.math.BigDecimal
import java.util.logging.Logger

@RestController
@RequestMapping("/v1/api/currency")
class CurrencyApi(currencyBl: CurrencyBl) {
    companion object {
        private val logger = Logger.getLogger(CurrencyApi::class.java.name)
    }

    private val currencyBl: CurrencyBl

    init {
        this.currencyBl = currencyBl
    }

    @GetMapping
    @Throws(CurrencyException::class, CurrencyServiceException::class, IOException::class)
    fun currency(
        @RequestParam to: String,
        @RequestParam from: String,
        @RequestParam amount: BigDecimal
    ): ResponseDto {
        logger.info("Iniciando la llamada a la API")
        val result: ResponseDto = currencyBl.currency(to, from, amount)
        logger.info(result.toString())
        logger.info("Finalizando la llamada a la API")
        return result
    }
}
