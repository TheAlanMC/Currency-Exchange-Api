package bo.edu.ucb.currencykt.api

import bo.edu.ucb.currencykt.bl.CurrencyBl
import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import org.slf4j.LoggerFactory
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.math.BigDecimal
import java.security.Principal

@RestController
@RequestMapping("/v1/api/currency")
class CurrencyApi(currencyBl: CurrencyBl) {
    companion object {
        private val logger = LoggerFactory.getLogger(CurrencyApi::class.java.name)
    }

    private val currencyBl: CurrencyBl

    init {
        this.currencyBl = currencyBl
    }

    @GetMapping
//    @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
    @Throws(CurrencyException::class, CurrencyServiceException::class, IOException::class)
    fun currency(
        @RequestParam to: String,
        @RequestParam from: String,
        @RequestParam amount: BigDecimal
    ): ResponseDto {
        logger.info("Starting the API call")
        val result: ResponseDto = currencyBl.currency(to, from, amount)
        logger.info("Finishing the API call")
        return result
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    fun user(): String {
        logger.info("Hello user")
        return "Hello user"
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun admin(): String {
        logger.info("Hello admin")
        return "Hello admin"
    }

    @GetMapping("/principal")
    fun info(principal: Principal): String {
        return principal.toString()
    }

}
