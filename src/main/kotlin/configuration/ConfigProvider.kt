package configuration

interface ConfigProvider {
    fun getConfig(): Config
}

data class Config(val apiToken: String, val chatId: String)