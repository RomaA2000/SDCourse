import database.Reactive
import server.Controller
import server.Result
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServer
import org.apache.log4j.BasicConfigurator
import rx.Observable

fun main() {
    val controller = Controller(Reactive())
    BasicConfigurator.configure()
    HttpServer
        .newServer(8000)
        .start { req, resp ->
            val uri = req.decodedPath
            val result: Result = if (uri.isNullOrEmpty()) {
                Result(HttpResponseStatus.BAD_REQUEST, Observable.just("error in request"))
            } else {
                val process = controller.process(uri.substring(1), req.queryParameters)
                process
            }
            resp.status = result.status
            val process = resp.writeString(result.message)
            process
        }
        .awaitShutdown()
}