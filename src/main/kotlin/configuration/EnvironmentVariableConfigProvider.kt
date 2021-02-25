package configuration

class EnvironmentVariableConfigProvider(
    private val environmentVariableResolver: EnvironmentVariableResolver
) : ConfigProvider {
    override fun getConfig(): Config =
        Config(
            environmentVariableResolver.resolve("API_TOKEN"),
            environmentVariableResolver.resolve("CHAT_ID")
        )
}