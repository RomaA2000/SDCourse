package tokenizer.token

import visitor.TokenVisitor

open class BraceToken(name: String): Token(name) {
    override fun visit(visitor: TokenVisitor<*>) = visitor.goTo(this)
}

object RIGHT : BraceToken("RIGHT")
object LEFT : BraceToken("LEFT")