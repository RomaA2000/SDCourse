package visitor

import tokenizer.token.*
import java.util.ArrayDeque

class PolishVisitor : TokenVisitor<List<Token>> {
    private val stack = ArrayDeque<Token>()
    private val polish = mutableListOf<Token>()

    override fun goTo(value: NumToken) {
        polish += value
    }

    override fun goTo(value: BraceToken) {
        when (value) {
            RIGHT -> {
                while (stack.isNotEmpty() && stack.first() !is LEFT) {
                    polish += stack.pop()
                }
                if (stack.isEmpty()) {
                    error("Error in token seq")
                }
                stack.pop()
            }
            LEFT -> stack.push(LEFT)
        }
    }

    override fun goTo(value: OpToken) {
        while (
            stack.isNotEmpty()
            && stack.peek() is OpToken
            && !priority(stack.peek() as OpToken, value)
        ) {
            polish += stack.pop()
        }
        stack.push(value)
    }

    override fun goToAll(values: List<Token>): List<Token> {
        values.forEach { it.visit(this) }
        while (stack.isNotEmpty()) {
            val element = stack.pop()
            if (element !is BraceToken) {
                polish += element
            }
        }
        return polish
    }
}