package visitor

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tokenizer.Tokenizer


class CalculatorVisitorTest {
    private fun expressionTest(expression: String, value: Int) {
        Assertions.assertEquals(CalculatorVisitor().goToAll(Tokenizer(expression).tokenize()), value)
    }

    @Test
    fun number() = expressionTest("1", 1)

    @Test
    fun numberBig() = expressionTest("123", 123)

    @Test
    fun add() = expressionTest("123 + 1", 124)

    @Test
    fun sub() = expressionTest("101 - 2", 99)

    @Test
    fun subNegative() = expressionTest("10 - 20", -10)

    @Test
    fun mul() = expressionTest("101 * 2", 202)

    @Test
    fun div() = expressionTest("3 / 2", 1)

    @Test
    fun test1() = expressionTest("1 + 13 - 4", 10)

    @Test
    fun test2() = expressionTest("1 * 2 * 3", 6)

    @Test
    fun test3() = expressionTest("((1 * 2 * 3) - 1) * 2", 10)

    @Test
    fun test4() = expressionTest("(1 - 2) * (1 - 2)", 1)
}