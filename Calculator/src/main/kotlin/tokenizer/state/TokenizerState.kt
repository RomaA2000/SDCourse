package tokenizer.state

import tokenizer.token.Token

abstract class TokenizerState(protected val tokens: MutableList<Token>) {

    open fun get(): List<Token> = tokens

    abstract fun next(char: Char): TokenizerState
}