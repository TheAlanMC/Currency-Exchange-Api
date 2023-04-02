package bo.edu.ucb.currencykt.command

import bo.edu.ucb.currencykt.service.EmailService

class EmailCommand(
    private val emailService: EmailService,
    private val to: String,
    private val subject: String,
    private val content: String
) : Command {

    fun getTo(): String {
        return to
    }

    fun getSubject(): String {
        return subject
    }

    fun getContent(): String {
        return content
    }

    override fun execute() {
        emailService.sendEmail(to, subject, content)
    }
}
