package bo.edu.ucb.currencykt.command

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailQueueProcessor(private val queue: CommandQueue, private val emailCommandFactory: EmailCommandFactory) {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(EmailQueueProcessor::class.java.name)
    }

    // Process the queue every 5 minutes
    // @Scheduled(fixedRate = 300000)
    @Scheduled(cron = "0 0/5 * * * *")
    fun processQueue() {
        logger.info("Starting processing the email queue")
        // Get all commands in the queue and group them by to
        val commands = queue.getCommands()
        // Process the command queue elements as EmailCommand
        val groupedCommands : Map<String, List<EmailCommand>> = commands.map { it as EmailCommand }.groupBy { it.getTo() }
        for ((_, emailCommands) in groupedCommands) {
            val combinedContent = emailCommands.joinToString("\n") { it.getContent() }
            val command = emailCommands.first()
            val updatedCommand = emailCommandFactory.create(command.getTo(), command.getSubject(), combinedContent)
            logger.info("Sending the email to ${command.getTo()}")
            updatedCommand.execute()
        }
        queue.clear()
        logger.info("Finishing processing the email queue")
    }
}