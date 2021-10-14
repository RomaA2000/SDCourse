package url

import com.xebialabs.restito.server.StubServer
import org.glassfish.grizzly.http.Method
import org.glassfish.grizzly.http.util.HttpStatus
import org.junit.Assert
import java.io.UncheckedIOException
import java.util.function.Consumer

import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.status
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.semantics.Condition.startsWithUri
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

const val PORT = 2222

class URLReaderWithStubServerTest {
    private val urlReader: URLReader = URLReader()

    @Test
    fun urlToResponse() {
        withStubServer(PORT) { s: StubServer? ->
            whenHttp(s)
                .match(
                    method(Method.GET),
                    startsWithUri("/ping")
                )
                .then(stringContent("pong"))
            val result: String =
                urlReader.urlToResponse("http://localhost:$PORT/ping")
            Assert.assertEquals("pong", result)
        }
    }

    @Test
    fun urlToResponseWithNotFoundError() {
        Assertions.assertThrows(UncheckedIOException::class.java) {
            withStubServer(PORT) { s: StubServer? ->
                whenHttp(s)
                    .match(
                        method(Method.GET),
                        startsWithUri("/ping")
                    )
                    .then(status(HttpStatus.NOT_FOUND_404))
                urlReader.urlToResponse("http://localhost:$PORT/ping")
            }
        }
    }

    private fun withStubServer(port: Int, callback: Consumer<StubServer?>) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback.accept(stubServer)
        } finally {
            stubServer?.stop()
        }
    }
}