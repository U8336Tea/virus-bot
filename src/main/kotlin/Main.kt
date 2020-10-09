import bot.Infector
import bot.event.*
import bot.command.*
import config.Config

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter

import java.util.*

fun main() {
    Config.loadPath("resources/config.json")

    val token = Config.token ?: System.getenv("TOKEN")!!

    val jda = JDABuilder
        .createDefault(token)
        .setChunkingFilter(ChunkingFilter.ALL)
        .enableIntents(GatewayIntent.GUILD_MEMBERS)
        .addEventListeners(OnMessageReceived, OnGuildMemberRoleAdd)
        .build()

    jda.awaitReady()
    val infector = createInfector(jda)

    // TODO: maybe an even better way to do this?
    Global.infector = infector

    OnMessageReceived.commands.add(InfectCommand)
    OnMessageReceived.commands.add(ChangeDelayCommand)
    OnMessageReceived.commands.add(DisinfectCommand)
//    OnMessageReceived.commands.add(KillCommand)

    val timer = Timer()

    // The bot does not use .scheduleAtFixedRate so that the infection rate can be changed at will.
    timer.schedule(object : TimerTask() {
        override fun run() {
            infector.infectRandom()
            timer.schedule(this, Config.infectMinutes * 60 * 1000)
        }
    }, Config.infectMinutes * 60 * 1000)
}

// TODO: Maybe optionally have multiple guilds?
fun createInfector(jda: JDA): Infector {
    val guild = jda.getGuildById(Config.guildID)!!
    val infectedRole = guild.getRoleById(Config.infectedID)!!
    val deadRole = guild.getRoleById(Config.deadID)!!
    val curedRole = guild.getRoleById(Config.curedID)!!
    val susceptibleRoles = Config.susceptibleIDs.map { guild.getRoleById(it)!! }

    return Infector(guild, infectedRole, deadRole, curedRole, susceptibleRoles)
}