package bo.edu.ucb.currencykt.exception

class CurrencyServiceException : Exception {
    constructor() : super() {}
    constructor(message: String) : super(message) {}
}