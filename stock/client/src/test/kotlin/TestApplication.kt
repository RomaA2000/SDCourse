import org.koin.core.context.startKoin

import org.koin.core.parameter.DefinitionParameters
import org.koin.core.scope.Scope

import org.koin.dsl.bind
import org.koin.dsl.module

import server.Server
import server.TestServerProvider

object TestApplication {
    val app = startKoin {
        printLogger()
        val definition: Scope.(DefinitionParameters) -> TestServerProvider = { TestServerProvider() }
        val module = module {
            single(definition = definition) bind Server::class
        }
        val modules = modules(
            module,
            appModule
        )
    }.koin

    inline fun <reified T : Any> get() = app.get<T>()
}
