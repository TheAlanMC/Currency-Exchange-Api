package bo.edu.ucb.currencykt.dto

import java.math.BigDecimal

data class RequestDto (
    val to: String,
    val from: String,
    val amount: BigDecimal
)