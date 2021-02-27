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