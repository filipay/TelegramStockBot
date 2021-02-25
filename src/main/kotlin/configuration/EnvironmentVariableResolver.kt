package configuration

class EnvironmentVariableResolver {
    fun resolve(environmentVariable: String): String =
        System.getenv(environmentVariable) ?: throw IllegalStateException("Missing environment variable: '$environmentVariable'")
}