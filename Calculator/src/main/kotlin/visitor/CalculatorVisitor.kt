package visitor

import tokenizer.token.*

class CalculatorVisitor : TokenVisitor<Int>  {
    private val stack = ArrayDeque<Int>()

    private fun binaryExec(operation: (Int, Int) -> Int) {
        if (stack.size < 2) {
            error("Not enough tokens for binary operation")
        }
        val first = stack.removeLast()
        val second = stack.removeLast()
        stack.addLast(operation(second, first))
    }

    override fun goTo(value: NumToken) {
        stack.addLast(value.value)
    }

    override fun goTo(value: BraceToken) {}

    override fun goTo(value: OpToken) {
        when (value) {
            ADD -> binaryExec(Int::plus)
            MUL -> binaryExec(Int::times)
            SUB -> binaryExec(Int::minus)
            DIV -> binaryExec(Int::div)
        }
    }

    override fun goToAll(values: List<Token>): Int {
        PolishVisitor().goToAll(values).forEach { it.visit(this) }
        if (stack.size == 1) {
            return stack.removeLast()
        } else {
            error("Not all tokens are in right places")
        }
    }
}