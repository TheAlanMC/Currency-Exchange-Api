package bo.edu.ucb.currencykt.config

import bo.edu.ucb.currencykt.dto.ErrorDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(CurrencyException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequest(e: CurrencyException): MutableMap<String, String?>? {
        return java.util.Map.of("error", e.message)
    }

    @ExceptionHandler(CurrencyServiceException::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Throws(
        JsonProcessingException::class
    )
    fun handleInternalServerError(e: CurrencyServiceException): ErrorDto {
        return errorDto(e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleInternalServerError(e: Exception): Map<String, String?> {
        return java.util.Map.of("error", e.message)
    }

    @Throws(JsonProcessingException::class)
    fun errorDto(response: String): ErrorDto {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(response)
    }
}