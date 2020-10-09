package bot.command

import Global.infector
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object DisinfectCommand : Command{
    override val name = "disinfect"

    override fun run(event: MessageReceivedEvent) {
        val user = event.message.mentionedMembers.firstOrNull()
        if (user == null) {
            event.channel.sendMessage("Usage: infect <user>")
            return
        }

        infector.disinfect(user)
    }
}