package config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MemoryConfig {
    @Bean
    fun taskListStorage(): TaskStorage {
        return MemoryTaskStorage()
    }
}