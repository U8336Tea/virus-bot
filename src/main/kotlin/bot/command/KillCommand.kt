package bot.command

import Global.infector
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object KillCommand : Command {
    override val name: String = "kill"

    override fun run(event: MessageReceivedEvent) {
        val user = event.message.mentionedMembers.firstOrNull()
        if (user == null) {
            event.channel.sendMessage("Usage: kill <user>")
            return
        }

        infector.kill(user)
    }
}