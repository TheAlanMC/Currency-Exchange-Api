package bo.edu.ucb.currencykt.dao.repository

import bo.edu.ucb.currencykt.dao.Currency
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(exported = false)
interface CurrencyRepository: CrudRepository<Currency, Long>