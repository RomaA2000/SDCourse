package container

import org.testcontainers.containers.GenericContainer

class StockServerContainer : GenericContainer<StockServerContainer>("stock")
