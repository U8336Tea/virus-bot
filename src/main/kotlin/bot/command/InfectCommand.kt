package bot.command

import Global.infector
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object InfectCommand : Command {
    override val name: String = "infect"

    override fun run(event: MessageReceivedEvent) {
        val user = event.message.mentionedMembers.firstOrNull()
        if (user == null) {
            event.channel.sendMessage("Usage: infect <user>")
            return
        }

        infector.infect(user)
    }
}