package bo.edu.ucb.currencykt.command

import bo.edu.ucb.currencykt.dto.ResponseDto
import bo.edu.ucb.currencykt.service.EmailService
import java.util.*

class EmailCommand(
    private val emailService: EmailService,
    var to: String,
    var subject: String,
    var content: String
) : Command {

    override fun execute() {
        emailService.sendEmail(to, subject, content)
    }
}
