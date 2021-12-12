package visitor

import tokenizer.token.BraceToken
import tokenizer.token.NumToken
import tokenizer.token.OpToken
import tokenizer.token.Token
import java.io.Writer

class WriterVisitor(private val output: Writer) : TokenVisitor<Unit> {
    override fun goTo(value: NumToken) {
        output.write(value.toString())
    }

    override fun goTo(value: BraceToken) {
        output.write(value.toString())
    }

    override fun goTo(value: OpToken) {
        output.write(value.toString())
    }

    override fun goToAll(values: List<Token>) {
        PolishVisitor().goToAll(values).forEach {
            it.visit(this)
            output.write(" ")
        }
    }
}