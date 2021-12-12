package tokenizer.state

import tokenizer.token.OpToken
import tokenizer.token.Token

class OpState(
    tokens: MutableList<Token>,
    op: OpToken
) : SingleCharState<OpToken>(tokens, op)