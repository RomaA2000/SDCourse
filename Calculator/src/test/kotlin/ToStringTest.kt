import tokenizer.token.Token
import java.util.stream.Collectors

open class ToStringTest {
    protected fun toString(tokens: List<Token>): String {
        return tokens.stream()
            .map {it.toString()}
            .collect(Collectors.joining(" "))
    }
}