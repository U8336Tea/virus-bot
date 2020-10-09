package bot.command

import config.Config
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ChangeDelayCommand : Command {
    override val name = "changedelay"
    override val aliases = arrayListOf("chgdelay", "chgd")

    override fun run(event: MessageReceivedEvent) {
        val delayString = event.message.contentRaw.split(" ").getOrNull(1)
        val delay = delayString?.toLongOrNull()
        if (delay == null) {
            event.channel.sendMessage("Usage: changedelay <delay in minutes>").queue()
            return
        }

        Config.infectMinutes = delay
    }
}