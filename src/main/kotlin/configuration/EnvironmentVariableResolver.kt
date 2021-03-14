package configuration

class EnvironmentVariableResolver {
    fun resolve(environmentVariable: String): String =
        System.getenv(environmentVariable)?.replace("\"", "")
            ?: throw IllegalStateException("Missing environment variable: '$environmentVariable'")
}