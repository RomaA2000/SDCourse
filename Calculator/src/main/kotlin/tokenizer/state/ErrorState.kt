package tokenizer.state

import tokenizer.token.Token

class ErrorState(tokens: MutableList<Token>, char: Char): TokenizerState(tokens) {

    val error = "Error with char: $char"

    override fun next(char: Char): TokenizerState = this
}