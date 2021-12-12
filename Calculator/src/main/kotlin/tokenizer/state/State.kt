package tokenizer.state

import tokenizer.token.*

open class State(tokens: MutableList<Token>) : TokenizerState(tokens) {
    override fun next(char: Char): TokenizerState = when (char) {
        ' ' -> this
        in '0'..'9' -> NumState(tokens, char - '0')
        '+' -> OpState(tokens, ADD)
        '*' -> OpState(tokens, MUL)
        '-' -> OpState(tokens, SUB)
        '/' -> OpState(tokens, DIV)
        '(' -> BraceState(tokens, LEFT)
        ')' -> BraceState(tokens, RIGHT)
        else -> ErrorState(tokens, char)
    }
}

