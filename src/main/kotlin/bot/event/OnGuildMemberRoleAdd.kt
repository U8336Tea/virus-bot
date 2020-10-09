package bot.event

import Global.infector
import config.Config

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent
import net.dv8tion.jda.api.hooks.EventListener

object OnGuildMemberRoleAdd : EventListener {
    override fun onEvent(event: GenericEvent) {
        if (event !is GuildMemberRoleAddEvent) return

        for (role in event.roles) {
            if (role.idLong == Config.curedID) {
                infector.disinfect(event.member)
                return
            }
        }
    }
}