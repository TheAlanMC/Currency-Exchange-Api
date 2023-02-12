package bo.edu.ucb.currencykt.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class ResponseDto (
    val success: Boolean,
    val query: RequestDto,
    val date: String,
    val result: BigDecimal
)
