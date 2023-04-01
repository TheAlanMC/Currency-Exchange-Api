package bo.edu.ucb.currencykt.command

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailScheduler(private val emailQueue: EmailQueue) {

    @Scheduled(cron = "0 30 17 * * *")
    fun executeEmailQueue() {
        println("Executing email queue")
        emailQueue.executeAll()
    }
}