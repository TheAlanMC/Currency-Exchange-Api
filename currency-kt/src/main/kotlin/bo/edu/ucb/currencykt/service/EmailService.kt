package bo.edu.ucb.currencykt.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EmailService @Autowired constructor(private val javaMailSender: JavaMailSender) {

    @Value("\${spring.mail.username}")
    private val from: String? = null

    @Async
    fun sendEmail(to: String?, subject: String?, content: String?) {
        val message = SimpleMailMessage()
        message.from = from
        message.setTo(to)
        message.subject = subject
        message.text = content
        javaMailSender.send(message)
    }
}