package tokenizer.token

import visitor.TokenVisitor

class NumToken(val value: Int): Token("NUMBER($value)") {
    override fun visit(visitor: TokenVisitor<*>) = visitor.goTo(this)
}