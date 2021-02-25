package configuration

import com.google.gson.Gson
import java.io.File

class FileConfigProvider(private val path: String, private val gson: Gson) : ConfigProvider {
    override fun getConfig(): Config {
        val file = File(path)
        val json = file.readText()
        return gson.fromJson(json, Config::class.java)
    }
}