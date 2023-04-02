package bo.edu.ucb.currencykt.command

import org.springframework.stereotype.Component
import java.util.*

@Component
class CommandQueue {
    private val queue = LinkedList<Command>()

    // Add a command to the queue
    fun add(command: Command) {
        queue.add(command)
    }

    // Get all commands in the queue
    fun getCommands(): List<Command> {
        return queue.toList()
    }

    // Remove all commands in the queue
    fun clear() {
        queue.clear()
    }

    // Get the size of the queue
    fun size(): Int {
        return queue.size
    }
}

