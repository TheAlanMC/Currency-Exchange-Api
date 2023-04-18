package bo.edu.ucb.currencykt.dto

import java.util.Date

data class NotificationDto (
    var message: String,
    var type: String,
    var date: Date
)