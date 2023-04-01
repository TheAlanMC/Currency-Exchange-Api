package bo.edu.ucb.currencykt.command

import org.springframework.stereotype.Component
import java.util.*

@Component
class EmailQueue {
    private val queue = LinkedList<EmailCommand>()

    fun add(command: EmailCommand) {
        queue.add(command)
    }

    fun executeAll() {
        val groupedCommands = queue.groupBy { it.to to it.subject }
        for ((_, commands) in groupedCommands) {
            val content = commands.joinToString("\n") { it.content }
            val command = commands.first()
            command.content = content
            command.execute()
        }
        queue.clear()
    }

    fun size(): Int {
        return queue.size
    }
}

