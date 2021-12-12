package visitor

import ToStringTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tokenizer.Tokenizer
import java.io.StringWriter

class WriterVisitorTest: ToStringTest() {

    private fun toStringTest(string: String, expected: String) {
        val stringWriter = StringWriter()
        WriterVisitor(stringWriter).goToAll(Tokenizer(string).tokenize())
        Assertions.assertEquals(expected, stringWriter.toString().trim())
    }

    @Test
    fun add() = toStringTest("1 + 2", "NUMBER(1) NUMBER(2) ADD")

    @Test
    fun mul() = toStringTest("2 * 1", "NUMBER(2) NUMBER(1) MUL")

    @Test
    fun sub() = toStringTest("4 - 2", "NUMBER(4) NUMBER(2) SUB")

    @Test
    fun div() = toStringTest("3 / 1", "NUMBER(3) NUMBER(1) DIV")

    @Test
    fun operations1() = toStringTest("3 - 1 * 0", "NUMBER(3) NUMBER(1) NUMBER(0) MUL SUB")

    @Test
    fun operations2() = toStringTest("(10 - 3) / 4", "NUMBER(10) NUMBER(3) SUB NUMBER(4) DIV")

    @Test
    fun operations3() = toStringTest("((1 * 2 * 3) - 1) * 2",
        "NUMBER(1) NUMBER(2) MUL NUMBER(3) MUL NUMBER(1) SUB NUMBER(2) MUL")

    @Test
    fun operations4() = toStringTest("(1 - 2) * (1 - 2)",
        "NUMBER(1) NUMBER(2) SUB NUMBER(1) NUMBER(2) SUB MUL")

}