package bo.edu.ucb.currencykt.config

import bo.edu.ucb.currencykt.dto.ErrorDto
import bo.edu.ucb.currencykt.dto.ErrorResponseDto
import bo.edu.ucb.currencykt.exception.CurrencyException
import bo.edu.ucb.currencykt.exception.CurrencyServiceException
import com.fasterxml.jackson.core.JsonProcessingException
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
    fun handleBadRequest(e: CurrencyException):ErrorDto {
        val errorResponseDto = ErrorResponseDto("bad request", e.message!!)
        return ErrorDto(errorResponseDto)
    }

    @ExceptionHandler(CurrencyServiceException::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleInternalServerError(e: CurrencyServiceException): ErrorDto {
        return errorDto(e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleInternalServerError(e: Exception): ErrorDto {
        val errorResponseDto = ErrorResponseDto("internal server error", e.message!!)
        return ErrorDto(errorResponseDto)
    }

    fun errorDto(response: String): ErrorDto {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(response)
    }
}