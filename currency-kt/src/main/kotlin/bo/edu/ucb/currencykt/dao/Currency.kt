package bo.edu.ucb.currencykt.dao

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.util.Date
import javax.persistence.*
@Entity
@Table(name = "currency")
class Currency (
    var currencyFrom: String,
    var currencyTo: String,
    var amount: BigDecimal,
    var result: BigDecimal,
    var date: Date,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    var id: Long = 0,
){
    constructor() : this("", "", BigDecimal.ZERO, BigDecimal.ZERO, Date(), 0)
}