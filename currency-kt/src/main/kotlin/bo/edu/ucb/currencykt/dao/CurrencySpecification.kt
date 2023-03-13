package bo.edu.ucb.currencykt.dao

import org.springframework.data.jpa.domain.Specification
import java.util.Date

class CurrencySpecification {

    companion object {
        fun currencyFrom(currencyFrom: String): Specification<Currency>{
            return Specification { root, query, cb ->
                cb.like(cb.lower(root.get("currencyFrom")), "%${currencyFrom.lowercase()}%")
            }
        }

        fun currencyTo(currencyTo: String): Specification<Currency>{
            return Specification { root, query, cb ->
                cb.like(cb.lower(root.get("currencyTo")), "%${currencyTo.lowercase()}%")
            }
        }

        fun dateBetween(dateFrom: Date, dateTo: Date): Specification<Currency>{
            return Specification { root, query, cb ->
                cb.between(root.get("date"), dateFrom, dateTo)
            }
        }
    }

}