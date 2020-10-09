package bot.event

import bot.command.Command
import config.Config

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener

object OnMessageReceived : EventListener {
    val commands = mutableListOf<Command>()

    override fun onEvent(event: GenericEvent) {
        if (event !is MessageReceivedEvent) return

        // Make sure the member who sent the message is allowed
        val roleIDs = event.member?.roles?.map { it.idLong } ?: return
        if (roleIDs.intersect(Config.privilegedIDs).isEmpty()) return

        val content = event.message.contentRaw
        if (!content.startsWith(Config.prefix)) return

        val commandName = content.split(" ")[0].substring(Config.prefix.length)
        for (command in commands) {
            val lower = commandName.toLowerCase()
            if (command.name == lower || command.aliases.contains(lower)) {
                event.message.addReaction("✅").queue()
                command.run(event)
            }
        }
    }
}