package bo.edu.ucb.currencykt.dto

import bo.edu.ucb.currencykt.dao.Currency

class CurrencyListDto (
    val totalItems: Int,
    val currencies: List<Currency>,
    val totalPages: Int,
    val currentPage: Int
)