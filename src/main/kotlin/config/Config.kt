package config

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

// TODO: A more idiomatic way to access a bunch of properties?
object Config {
    private lateinit var internalData: ConfigData

    fun loadString(string: String) {
        internalData = Json.decodeFromString(string)
    }

    @Throws(IOException::class)
    fun loadFile(file: File) {
        loadString(file.readText())
    }

    @Throws(IOException::class)
    fun loadPath(path: String) {
        loadFile(File(path))
    }

    fun saveString(): String {
        return Json.encodeToString(internalData)
    }

    @Throws(IOException::class)
    fun saveFile(file: File) {
        file.writeText(saveString())
    }

    @Throws(IOException::class)
    fun savePath(path: String) {
        saveFile(File(path))
    }

    val token get() = internalData.token

    val prefix get() = internalData.prefix

    val guildID get() = internalData.guildID
    val infectedID get() = internalData.infectedID
    val deadID get() = internalData.deadID
    val curedID get() = internalData.curedID
    val susceptibleIDs get() = internalData.susceptibleIDs
    val privilegedIDs get() = internalData.privilegedIDs

    var killHours
        get() = internalData.killHours
        set(value) {
            internalData.killHours = value
        }

    var infectMinutes
        get() = internalData.infectMinutes
        set(value) {
            internalData.infectMinutes = value
        }
}