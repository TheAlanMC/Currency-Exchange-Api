package bo.edu.ucb.currencykt.dao.repository

import bo.edu.ucb.currencykt.dao.Currency
import org.springframework.data.repository.CrudRepository

interface CurrencyRepository: CrudRepository<Currency, Long>