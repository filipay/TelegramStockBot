package exchanges.dispatchers

interface EventDispatcher<T> {
    fun onEvent(event: T)
}