package visitor

import ToStringTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tokenizer.Tokenizer

class PolishVisitorTest : ToStringTest() {
    private fun polishTest(string: String, expected: String) {
        Assertions.assertEquals(
            expected, toString(PolishVisitor().goToAll(Tokenizer(string).tokenize()))
        )
    }

    @Test
    fun add() = polishTest("1 + 2", "NUMBER(1) NUMBER(2) ADD")

    @Test
    fun mul() = polishTest("2 * 1", "NUMBER(2) NUMBER(1) MUL")

    @Test
    fun sub() = polishTest("4 - 2", "NUMBER(4) NUMBER(2) SUB")

    @Test
    fun div() = polishTest("3 / 1", "NUMBER(3) NUMBER(1) DIV")

    @Test
    fun operations1() = polishTest("3 - 1 * 0", "NUMBER(3) NUMBER(1) NUMBER(0) MUL SUB")

    @Test
    fun operations2() = polishTest("(10 - 3) / 4", "NUMBER(10) NUMBER(3) SUB NUMBER(4) DIV")

    @Test
    fun operations3() = polishTest("((1 * 2 * 3) - 1) * 2", "NUMBER(1) NUMBER(2) MUL NUMBER(3) MUL NUMBER(1) SUB NUMBER(2) MUL")

    @Test
    fun operations4() = polishTest("(1 - 2) * (1 - 2)", "NUMBER(1) NUMBER(2) SUB NUMBER(1) NUMBER(2) SUB MUL")
}