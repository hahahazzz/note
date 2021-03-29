## IDEA中kotlin项目搭配Gradle打包jar

    ```
    jar {
        manifest {
            attributes["Main-Class"] = "<package.MainClass>"
        }
        // This line of code recursively collects and copies all of a project's files
        // and adds them to the JAR itself. One can extend this task, to skip certain
        // files or particular types at will
        from { configurations.compileClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
    }
    ```