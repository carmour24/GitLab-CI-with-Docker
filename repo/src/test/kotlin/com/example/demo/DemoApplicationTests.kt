package com.example.demo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.Testcontainers
import java.nio.file.Paths
import java.io.File

class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoApplicationTests(@LocalServerPort private val port: Int) {
  private val cypress = startCypressContainer(port)

  @Test
  fun contextLoads() {
    val result = cypress.execInContainer("cypress", "run")

    val path = Paths.get("").toAbsolutePath().toString() + "/src"

    for (file in File(path).walk()) {
      println("${file.path}")
    }

    assertTrue(result.exitCode == 0)
  }
}

fun startCypressContainer(webServerPort: Int) = KGenericContainer("cypress/included:3.8.1").apply {
    val path = Paths.get("").toAbsolutePath().toString() + "/src/test/e2e/"
    val workingDirectory = "/e2e"
    
    Testcontainers.exposeHostPorts(webServerPort)

    withEnv("CYPRESS_baseUrl", "http://host.testcontainers.internal:${webServerPort}")
    withFileSystemBind(path, workingDirectory, BindMode.READ_WRITE)
    withCreateContainerCmdModifier({ containerCmd -> containerCmd
      .withTty(true)
      .withEntrypoint("/bin/sh")
    })
    withWorkingDirectory(workingDirectory)

    start()
}
