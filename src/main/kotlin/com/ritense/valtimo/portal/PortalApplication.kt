package com.ritense.valtimo.portal

import java.net.InetAddress
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableScheduling


@EnableScheduling
@SpringBootApplication
class PortalApplication

fun main(args: Array<String>) {
    val applicationContext = runApplication<PortalApplication>(*args)
    val environment: Environment = applicationContext.environment
    val logger = KotlinLogging.logger {}

    logger.info {
        """

        Application '${environment.getProperty("spring.application.name")}' is running!
        Local URL: [http://127.0.0.1:${environment.getProperty("server.port")}].
        External URL: [http://${InetAddress.getLocalHost().hostAddress}:${environment.getProperty("server.port")}]
        """.trimIndent()
    }
}
