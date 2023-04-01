package bo.edu.ucb.currencykt.api

//import org.springframework.security.access.prepost.PreAuthorize

import bo.edu.ucb.currencykt.bl.CurrencyBl
import bo.edu.ucb.currencykt.command.EmailCommand
import bo.edu.ucb.currencykt.command.EmailQueue
import bo.edu.ucb.currencykt.dao.Currency
import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.service.EmailService
import bo.edu.ucb.currencykt.util.KeycloakSecurityContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@RestController
@RequestMapping("/v1/api/currency")
class CurrencyApi @Autowired constructor(private val currencyBl: CurrencyBl, private val emailQueue: EmailQueue, private val emailService: EmailService) {
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

        logger.info("Starting the email queue")
        val email = KeycloakSecurityContextHolder.getEmail()
        logger.info("Email: $email")
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = currentDate.format(formatter)
        val subject = "Currency Exchange Rate for $date"
        val content = "The exchange rate for ${result.query.from} to ${result.query.to} is ${result.result}\n"
        val emailCommand = EmailCommand(emailService, email!!, subject, content)
        emailQueue.add(emailCommand)
        println("Email queue size: ${emailQueue.size()}")
        logger.info("Finishing the email queue")
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
