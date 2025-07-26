// ===============================
// File: loader/MinecraftVersionManager.kt
// ===============================

package loader

import java.io.File
import java.net.URLClassLoader

object MinecraftVersionManager {
    private val minecraftDir = File(System.getProperty("user.home"), ".minecraft")
    private val versionsDir = File(minecraftDir, "versions")

    lateinit var mcVersion: String
    lateinit var mcJar: File
    lateinit var mcClassLoader: URLClassLoader

    fun detectVersion(cliArg: String? = null) {
        mcVersion = cliArg ?: versionsDir.listFiles()
            ?.filter { it.isDirectory && File(it, "${it.name}.jar").exists() }
            ?.map { it.name }
            ?.maxOrNull() ?: error("No Minecraft versions found")

        mcJar = File(versionsDir, "$mcVersion/$mcVersion.jar")
        if (!mcJar.exists()) error("Minecraft jar for version $mcVersion not found at ${mcJar.absolutePath}")

        mcClassLoader = URLClassLoader(arrayOf(mcJar.toURI().toURL()), ClassLoader.getSystemClassLoader())

        println("[Fork] Using Minecraft version: $mcVersion")
    }

    fun getMappingsFile(): File {
        val mappingsFile = File("mappings/$mcVersion.tiny")
        if (!mappingsFile.exists()) error("Mappings file not found for version $mcVersion")
        return mappingsFile
    }
}
