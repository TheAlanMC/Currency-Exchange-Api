package bo.edu.ucb.currencykt.bl

import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal


@Service
class CurrencyBl {
    companion object {
        private val logger = LoggerFactory.getLogger(CurrencyBl::class.java.name)
    }

    @Value("\${currency.url}")
    private val url: String? = null

    @Value("\${currency.api_key}")
    private val apiKey: String? = null
    @Throws(CurrencyException::class, CurrencyServiceException::class, IOException::class)
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

    @Throws(CurrencyServiceException::class, IOException::class)
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
                return responseDto(stringResponse)
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

    @Throws(JsonProcessingException::class)
    fun responseDto(response: String): ResponseDto {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(response)
    }
}
