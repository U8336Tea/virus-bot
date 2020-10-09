package config

import kotlinx.serialization.Serializable

//TODO: Maybe this is kinda ugly too?
@Serializable
internal data class ConfigData(val token: String? = null,
                      val prefix: String,

                      val guildID: Long,
                      val infectedID: Long,
                      val deadID: Long,
                      val curedID: Long,
                      val susceptibleIDs: List<Long>,
                      val privilegedIDs: List<Long>,

                      var killHours: Long,
                      var infectMinutes: Long)