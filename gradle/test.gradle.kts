val testTask = tasks.named("test", Test::class)
testTask.configure {
    useJUnitPlatform {
        excludeTags("integration")
    }
    finalizedBy(renameTestReportTask)
}

val integrationTestTask = tasks.register("integrationTest", Test::class)
integrationTestTask.configure {
    group = "verification"
    useJUnitPlatform {
        includeTags("integration")
    }
    mustRunAfter(tasks.named("check"))
    finalizedBy(renameIntegrationTestReportTask)
}

val deleteIndexHtmlReportTask by tasks.register("deleteIndexHtmlReportTask", Delete::class)

val testReportOutputFolder = testTask.map { it.reports.html.outputLocation.get() }
val integrationTestReportOutputFolder = integrationTestTask.map { it.reports.html.outputLocation.get() }

val renameTestReportTask by tasks.register("renameTestReportTask", Copy::class) {
    from(testReportOutputFolder)
    into(testReportOutputFolder)
    include("index.html")
    rename("index.html", "unit-test-report.html")
    finalizedBy(deleteIndexHtmlReportTask)
    doLast {
        val testReportOutputFile = testReportOutputFolder.get().toString() + "/index.html"
        deleteIndexHtmlReportTask.delete(testReportOutputFile)
    }
}

val renameIntegrationTestReportTask by tasks.register("renameIntegrationTestSummaryReportTask", Copy::class) {
    from(integrationTestReportOutputFolder)
    into(integrationTestReportOutputFolder)
    include("index.html")
    rename("index.html", "integration-test-report.html")
    finalizedBy(deleteIndexHtmlReportTask)
    doLast {
        val testReportOutputFile = integrationTestReportOutputFolder.get().toString() + "/index.html"
        deleteIndexHtmlReportTask.delete(testReportOutputFile)
    }
}