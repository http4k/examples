rootProject.name = "http4k-examples"


rootDir.walkTopDown()
    .filter { it.isDirectory && File(it, "build.gradle.kts").exists() }
    .filterNot { dir -> dir == rootDir }
    .forEach {
        val module = it.relativeTo(rootDir).path
            .replace(File.separatorChar, '/')
            .replace('/', '-')
        include(":$module")
        project(":$module").projectDir = File(it.absoluteFile.path)
    }
