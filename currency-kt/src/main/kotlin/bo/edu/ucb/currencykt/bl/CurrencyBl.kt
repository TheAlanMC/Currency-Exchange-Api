package bo.edu.ucb.currencykt.bl

import bo.edu.ucb.currencykt.dao.Currency
import bo.edu.ucb.currencykt.dao.CurrencySpecification
import bo.edu.ucb.currencykt.dao.repository.CurrencyRepository
import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Service
class CurrencyBl @Autowired constructor(
    private val currencyRepository: CurrencyRepository
    )
{
    companion object {
        private val logger = LoggerFactory.getLogger(CurrencyBl::class.java.name)
    }

    @Value("\${currency.url}")
    private val url: String? = null

    @Value("\${currency.api_key}")
    private val apiKey: String? = null

    fun currency(to: String, from: String, amount: BigDecimal): ResponseDto {
        logger.info("Starting the Business Logic layer")
        if (amount < BigDecimal.ZERO) {
            logger.error("Amount must be greater than 0")
            throw CurrencyException("Amount must be greater than 0")
        }
        logger.info("Starting the external service call")
        val responseDto: ResponseDto = currencyService(to, from, amount)
        logger.info("Finishing the external service call")
        logger.info("Finishing the Business Logic layer")
        return responseDto
    }

    fun currencyService(to: String, from: String, amount: BigDecimal): ResponseDto {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("$url?from=$from&to=$to&amount=$amount")
            .addHeader("apiKey", apiKey)
            .build()
        val call: Call = client.newCall(request)
        try {
            val response: Response = call.execute()
            val stringResponse: String = response.body().string()
            if (response.isSuccessful) {
                logger.info("Successful external service call")
                val responseDto: ResponseDto = responseDto(stringResponse)
                val currency = Currency()
                currency.currencyTo = to
                currency.currencyFrom = from
                currency.amount = amount
                currency.result = responseDto.result
                currency.date = Date()
                currencyRepository.save(currency)
                return responseDto
            }
            logger.error("Error calling the external service")
            logger.error(stringResponse)
            throw CurrencyServiceException(stringResponse)
        } catch (e: IOException) {
            logger.error("Error calling the external service")
            logger.info(e.message)
            throw IOException(e.message)
        }
    }
    fun responseDto(response: String): ResponseDto {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(response)
    }

    fun all(orderBy: String?, order: String?, page: Int, size: Int, currencyFrom: String?, currencyTo:String?, dateFrom: String?,dateTo: String?): Page<Currency> {
        logger.info("Starting the Business Logic layer")
        val pageable: Pageable = getPageable(page, size, orderBy, order)
        val response: Page<Currency>
        var specification: Specification<Currency> = Specification.where(null)

        if (!dateFrom.isNullOrEmpty() && !dateTo.isNullOrEmpty()) {
            if (!validateDateInterval(dateFrom, dateTo)) {
                logger.error("Date interval is not valid")
                throw CurrencyException("Date interval is not valid")
            }
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFrom: Date = format.parse(dateFrom)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = format.parse(dateTo)
        calendar.add(Calendar.DATE, 1)
        val dateTo: Date = calendar.time
            specification = specification.and(specification.and(CurrencySpecification.dateBetween(dateFrom, dateTo)))
        }
        if (!currencyFrom.isNullOrEmpty()) {
            specification = specification.and(specification.and(CurrencySpecification.currencyFrom(currencyFrom)))
        }
        if (!currencyTo.isNullOrEmpty()) {
            specification = specification.and(CurrencySpecification.currencyTo(currencyTo))
        }
        response = currencyRepository.findAll(specification, pageable)
        logger.info("Finishing the Business Logic layer")
        return response
    }

    fun getPageable(page: Int, size: Int, orderBy: String?, order: String?): Pageable {
        val sort: Sort = (if (orderBy == null || order == null || orderBy.isEmpty() || order.isEmpty()) {
            Sort.by(Sort.Direction.ASC, "id")
        } else {
            Sort.by(Sort.Direction.fromString(order), orderBy)
        })
        return PageRequest.of(page, size, sort)
    }

    fun validateDateInterval(dateFrom: String, dateTo: String): Boolean {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFrom: Date = format.parse(dateFrom)
        val dateTo: Date = format.parse(dateTo)
        return dateFrom.before(dateTo) || dateFrom == dateTo
    }
}
