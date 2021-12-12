package tokenizer.token

import visitor.TokenVisitor

abstract class Token(private val name: String) {
    override fun toString(): String = name

    abstract fun visit(visitor: TokenVisitor<*>)
}

