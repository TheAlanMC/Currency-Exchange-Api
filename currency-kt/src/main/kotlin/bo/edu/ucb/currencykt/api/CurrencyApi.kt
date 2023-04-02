package bo.edu.ucb.currencykt.api

//import org.springframework.security.access.prepost.PreAuthorize

import bo.edu.ucb.currencykt.bl.CurrencyBl
import bo.edu.ucb.currencykt.dao.Currency
import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.util.KeycloakSecurityContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*


@RestController
@RequestMapping("/v1/api/currency")
class CurrencyApi @Autowired constructor(private val currencyBl: CurrencyBl) {
    companion object {
        private val logger = LoggerFactory.getLogger(CurrencyApi::class.java.name)
    }

    @GetMapping
//    @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
    fun currency(
        @RequestParam to: String,
        @RequestParam from: String,
        @RequestParam amount: BigDecimal
    ): ResponseDto {
        logger.info("Starting the API call")
        val result: ResponseDto = currencyBl.currency(to, from, amount)
        logger.info("Finishing the API call")
        // Get the email from the token
        val email = KeycloakSecurityContextHolder.getEmail()
        logger.info("Starting adding the result to the email queue for user $email")
        if (email != null)
            currencyBl.addEmailQueue(email, result)
        else
            logger.info("Email is not available")
        logger.info("Finishing adding the result to the email queue for user $email")
        return result
    }

    @GetMapping("/all")
    fun all(
        @RequestParam(required = false) orderBy: String?,
        @RequestParam(required = false) order: String?,
        @RequestParam(required = false) currencyFrom: String?,
        @RequestParam(required = false) currencyTo: String?,
        @RequestParam(required = false) dateFrom: String?,
        @RequestParam(required = false) dateTo: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<Currency> {
        logger.info("Starting the API call")
        val result: Page<Currency> = currencyBl.all(orderBy, order, page, size, currencyFrom, currencyTo, dateFrom, dateTo)
        logger.info("Finishing the API call")
        return result
    }
//
//    @GetMapping("/user")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    fun user(): String {
//        logger.info("Hello user")
//        return "Hello user"
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    fun admin(): String {
//        logger.info("Hello admin")
//        return "Hello admin"
//    }
//
//    @GetMapping("/principal")
//    fun info(principal: Principal): String {
//        return principal.toString()
//    }


}
