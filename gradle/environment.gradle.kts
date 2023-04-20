val environmentVariablesFileName = ".env.properties"

// loads environment variables when a JavaExec task is ran

tasks.withType<JavaExec> {
    println("Configuring environment variables")

    project.file(environmentVariablesFileName)
        .takeIf { it.exists() && it.isFile }
        ?.run {
            readLines()
                .filterNot { it.startsWith("#") || it.startsWith("//") }
                .map { it.split("=", limit = 2) }
                .forEach { systemProperty(it.first(), it.last()) }
        }
        ?: println("Skipping environment variable initialisation: No '$environmentVariablesFileName' file found.")
}