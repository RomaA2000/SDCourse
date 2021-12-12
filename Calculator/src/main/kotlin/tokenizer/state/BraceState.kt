package tokenizer.state

import tokenizer.token.BraceToken
import tokenizer.token.Token

class BraceState(
    tokens: MutableList<Token>,
    brace: BraceToken
) : SingleCharState<BraceToken>(tokens, brace)