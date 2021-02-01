package configuration

import com.google.gson.Gson
import java.io.File

class ConfigProvider(private val gson: Gson) {
    fun getConfig(path: String): Config{
        val file = File(path)
        val json = file.readText()
        return gson.fromJson(json, Config::class.java)
    }
}

data class Config(val apiToken: String, val chatId: String)