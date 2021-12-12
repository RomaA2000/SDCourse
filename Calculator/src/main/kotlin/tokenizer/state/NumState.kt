package tokenizer.state

import tokenizer.token.NumToken
import tokenizer.token.Token

class NumState(
    tokens: MutableList<Token>,
    private val number: Int
) : TokenizerState(tokens) {

    override fun next(char: Char): TokenizerState = when (char) {
        in '0'..'9' -> NumState(tokens, number * 10 + (char - '0'))
        else -> {
            tokens += NumToken(number)
            State(tokens).next(char)
        }
    }

    override fun get(): List<Token> {
        val processed = tokens.toMutableList()
        processed += NumToken(number)
        return processed
    }
}