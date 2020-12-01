package by.rymko.plugins.filediff

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class FileDiffPluginIntegrationTest extends Specification {

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        buildFile << """
            plugins {
                id 'by.rymko.file-diff'
            }
        """
    }

    def "can successfully diff 2 files"() {
        given:
            File fileTest1 = testProjectDir.newFile('testFile1.txt')
            File fileTest2 = testProjectDir.newFile('testFile2.txt')
        and:
            buildFile << """
            fileDiff {
                file1 = file('${fileTest1.getName()}')
                file2 = file('${fileTest2.getName()}')
            }
        """

        when:
            def result = GradleRunner.create()
                    .withProjectDir(testProjectDir.root)
                    .withArguments('fileDiff')
                    .withPluginClasspath()
                    .build()

        then:
            result.output.contains('Files have the same size.')
            result.task(':fileDiff').outcome == TaskOutcome.SUCCESS

    }
}
