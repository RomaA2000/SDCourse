package tokenizer.state

import tokenizer.token.Token

abstract class SingleCharState<T : Token>(
    tokens: MutableList<Token>,
    private val singleCharToken: T
) : TokenizerState(tokens) {

    override fun next(char: Char): TokenizerState {
        tokens += singleCharToken
        return State(tokens).next(char)
    }

    override fun get(): List<Token> {
        val processed = tokens.toMutableList()
        processed += singleCharToken
        return processed
    }
}

