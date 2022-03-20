package server

import org.koin.core.component.KoinComponent

class TestServerProvider : KoinComponent, Server {
    private var host: String = "localhost"
    private var port: Int = 80

    fun host(host: String) {
        this.host = host
    }

    fun port(port: Int) {
        this.port = port
    }

    override fun url(): String {
        return "http://${host}:${port}"
    }
}
