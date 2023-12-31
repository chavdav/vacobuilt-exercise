package me.chavdav.vacobuilt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig

@Configuration
class AppConfig {

    // TODO use config file or envvars to populate values
    val db = Database.connect(
        "jdbc:postgresql://localhost:5432/test",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
}
