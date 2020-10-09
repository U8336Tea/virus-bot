package bot

import config.Config

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role
import java.lang.Exception

import java.util.*
import kotlin.random.Random

class Infector(private val guild: Guild,
               private val infectedRole: Role,
               private val deadRole: Role,
               private val curedRole: Role,
               private val susceptibleRoles: List<Role>) {

    private val deathTimers = hashMapOf<Member, Timer>()
    private val oldRoles = hashMapOf<Member, Role>()

    private fun replaceRoles(member: Member, newRole: Role?, replacedRoles: List<Role>, addToOld: Boolean = false) {
        for (role in member.roles) {
            if (replacedRoles.contains(role)) {
                guild.removeRoleFromMember(member, role).queue()
                if (addToOld) oldRoles[member] = role
            }
        }

        guild.addRoleToMember(member, newRole ?: return).queue()
    }

    fun kill(member: Member) {
        replaceRoles(member, deadRole, susceptibleRoles + infectedRole)
    }

    fun infect(member: Member) {
        replaceRoles(member, infectedRole, susceptibleRoles, true)

        member.user.openPrivateChannel().queue {
            try {
                it.sendMessage("${guild.name}: You have been infected with the pooronavirus! Get a vaccine within " +
                            "${Config.killHours} hours or you will die.").queue()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                kill(member)
            }
        }, Config.killHours * 60 * 60 * 1000)

        // If an old timer exists, we don't want to have them both execute accidentally.
        deathTimers[member]?.cancel()
        deathTimers[member] = timer
    }

    fun disinfect(member: Member) {
        val oldRole = oldRoles[member]
        replaceRoles(member, oldRole, listOf(infectedRole))
        deathTimers[member]?.cancel()
        deathTimers.remove(member)
        oldRoles.remove(member)
    }

    // Maybe not strictly idiomatic, but it will help if this gets extended to work on multiple guilds
    fun infectRandom() {
        val susceptibleMembers = guild.members.filter {
            val roles = it.roles
            val intersection = roles.intersect(susceptibleRoles)

            intersection.isNotEmpty() && !roles.contains(curedRole)
        }

        val idx = Random.nextInt(susceptibleMembers.size)
        this.infect(susceptibleMembers[idx])
    }
}