package messaging

interface Formatter<T>  {
    fun format(payload: T): String
}

class NoOpFormatter: Formatter<String> {
    override fun format(payload: String): String = payload.also { println(it) }
}