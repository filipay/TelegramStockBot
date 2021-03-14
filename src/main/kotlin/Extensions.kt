infix fun Boolean.ifTrue(block: () -> Unit): Boolean {
    if(this) {
        block()
    }
    return this
}

infix fun Boolean.ifFalse(block: () -> Unit): Boolean {
    if(!this) {
        block()
    }
    return this
}

fun <T: AutoCloseable> T.use(block: (T) -> Unit) {
    block(this)
    close()
}