package tokenizer

import ToStringTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TokenizerTest : ToStringTest() {
    private fun toStringTest(string: String, expected: String) {
        Assertions.assertEquals(expected, toString(Tokenizer(string).tokenize()))
    }

    @Test
    fun number() = toStringTest("1", "NUMBER(1)")

    @Test
    fun numberBig() = toStringTest("22", "NUMBER(22)")

    @Test
    fun add() = toStringTest("22 + 1", "NUMBER(22) ADD NUMBER(1)")

    @Test
    fun mul() = toStringTest("11 * 2", "NUMBER(11) MUL NUMBER(2)")

    @Test
    fun sub() = toStringTest("4 - 2", "NUMBER(4) SUB NUMBER(2)")

    @Test
    fun div() = toStringTest("3 / 1", "NUMBER(3) DIV NUMBER(1)")

    @Test
    fun brace1() = toStringTest(
        "3 * (4 + 0) * 2",
        "NUMBER(3) MUL LEFT NUMBER(4) ADD NUMBER(0) RIGHT MUL NUMBER(2)"
    )

    @Test
    fun brace2() = toStringTest(
        "((1 * 2 * 3) - 1) * 2",
        "LEFT LEFT NUMBER(1) MUL NUMBER(2) MUL NUMBER(3) RIGHT SUB NUMBER(1) RIGHT MUL NUMBER(2)"
    )
}