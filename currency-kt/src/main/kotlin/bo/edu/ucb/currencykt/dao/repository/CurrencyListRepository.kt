package bo.edu.ucb.currencykt.dao.repository
import bo.edu.ucb.currencykt.dao.Currency
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*


interface CurrencyListRepository  : PagingAndSortingRepository<Currency, Long> {
    @Query("SELECT c FROM Currency c WHERE c.date BETWEEN ?1 AND ?2")
    fun findAllByDateBetween(startDate: Date, endDate: Date, pageable: Pageable): Page<Currency>
    override fun findAll(pageable: Pageable): Page<Currency>
}

