package bot.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

interface Command {
    val name: String
    val aliases: ArrayList<String>
        get() = arrayListOf<String>()

    fun run(event: MessageReceivedEvent)
}