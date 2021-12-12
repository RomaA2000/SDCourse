package visitor

import tokenizer.token.BraceToken
import tokenizer.token.NumToken
import tokenizer.token.OpToken
import tokenizer.token.Token

interface TokenVisitor<T> {
    fun goTo(value: NumToken)
    fun goTo(value: BraceToken)
    fun goTo(value: OpToken)
    fun goToAll(values: List<Token>): T
}
