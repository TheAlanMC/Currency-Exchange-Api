package bo.edu.ucb.currencykt.bl

import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal
import java.util.logging.Logger


@Service
class CurrencyBl {
    companion object {
        private val logger = Logger.getLogger(CurrencyBl::class.java.name)
    }

    @Value("\${currency.url}")
    private val url: String? = null

    @Value("\${currency.api_key}")
    private val api_key: String? = null
    @Throws(CurrencyException::class, CurrencyServiceException::class, IOException::class)
    fun currency(to: String, from: String, amount: BigDecimal): ResponseDto {
        if (amount < BigDecimal.ZERO) {
            logger.warning("El monto debe ser mayor a 0")
            throw CurrencyException("El monto debe ser mayor a 0")
        }
        logger.info("Iniciando la llamada al servicio externo")
        val responseDto: ResponseDto = currencyService(to, from, amount)
        logger.info("Finalizando la llamada al servicio externo")
        return responseDto
    }

    @Throws(CurrencyServiceException::class, IOException::class)
    fun currencyService(to: String, from: String, amount: BigDecimal): ResponseDto {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("$url?from=$from&to=$to&amount=$amount")
            .addHeader("apiKey", api_key)
            .build()
        val call: Call = client.newCall(request)
        try {
            val response: Response = call.execute()
            val stringResponse: String = response.body().string()
            if (response.isSuccessful) {
                logger.info("Consumo exitoso del servicio externo")
                return responseDto(stringResponse)
            }
            logger.warning("Error al consumir el servicio externo")
            logger.info(stringResponse)
            throw CurrencyServiceException(stringResponse)
        } catch (e: IOException) {
            logger.warning("Error al consumir el servicio externo")
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
