package bo.edu.ucb.currencykt.dao.repository
import bo.edu.ucb.currencykt.dao.Currency
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(exported = false)
interface CurrencyRepository  : PagingAndSortingRepository<Currency, Long> {
    fun findAll(specification: Specification<Currency>, pageable: Pageable): Page<Currency>

}

