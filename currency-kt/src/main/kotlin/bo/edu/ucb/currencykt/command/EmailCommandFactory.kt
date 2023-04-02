package bo.edu.ucb.currencykt.command

import bo.edu.ucb.currencykt.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmailCommandFactory @Autowired constructor(private val emailService: EmailService) {

    // Currying function to create a new EmailCommand
    // Create a new EmailCommand
    fun create(to: String, subject: String, content: String): EmailCommand {
        return EmailCommand(emailService, to, subject, content)
    }
}