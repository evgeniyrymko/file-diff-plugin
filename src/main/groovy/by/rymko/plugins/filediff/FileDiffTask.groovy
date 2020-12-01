package by.rymko.plugins.filediff

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class FileDiffTask extends DefaultTask {

    @InputFile
    Property<File> file1

    @InputFile
    Property<File> file2

    @OutputFile
    File resultFile = new File("${project.buildDir}/diff-result.txt")

    @TaskAction
    def diff() {
        String diffResult

        if (file1.get().size() == file2.get().size()) {
            diffResult = "Files have the same size."
        } else {
            File largestFile = file1.get().size() > file2.get().size() ? file1.get() : file2.get()
            diffResult = "${largestFile.toString()} is larger and has a size ${largestFile.size()} bytes."
        }

        resultFile.write(diffResult)
        println("Result file is written to $resultFile")
        println(diffResult)
    }

}
