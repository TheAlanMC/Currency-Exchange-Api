package bo.edu.ucb.currencykt.dto

import java.math.BigDecimal

class RequestDto (
    val to: String,
    val from: String,
    val amount: BigDecimal
)