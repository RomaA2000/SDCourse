package tokenizer.token

import visitor.TokenVisitor

open class OpToken(name: String): Token(name) {
    override fun visit(visitor: TokenVisitor<*>) = visitor.goTo(this)
}

object ADD : OpToken("ADD")
object MUL : OpToken("MUL")
object DIV : OpToken("DIV")
object SUB : OpToken("SUB")

fun priority(l: OpToken, r: OpToken): Boolean {
    return (l is ADD || l is SUB) && (r is MUL || r is DIV)
}