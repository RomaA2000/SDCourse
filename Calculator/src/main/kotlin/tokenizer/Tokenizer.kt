package tokenizer

import tokenizer.state.ErrorState
import tokenizer.state.State
import tokenizer.state.TokenizerState
import tokenizer.token.Token

class Tokenizer(private val data: String) {
    private var state: TokenizerState = State(mutableListOf())

    fun tokenize(): List<Token> {
        data.forEach { char ->
            state = state.next(char)
        }
        if (state is ErrorState) {
            error((state as ErrorState).error)
        }
        return state.get()
    }
}