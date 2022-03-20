import org.koin.core.context.startKoin

import org.koin.dsl.bind
import org.koin.dsl.module

import server.BaseServer
import getter.Getter
import server.Server

fun main() {
    val application = startKoin {
        val module = module {
            single { BaseServer() } bind Server::class
        }
        val koinApplication = modules(
            module,
            appModule
        )
    }

    val getter = application.koin.get<Getter>()
    getter.server.start(wait = true)
}
