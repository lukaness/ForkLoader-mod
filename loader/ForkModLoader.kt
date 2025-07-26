// ===============================
// File: loader/ForkModLoader.kt
// ===============================

package loader

import api.ForkModInitializer
import net.fabricmc.tinyremapper.TinyRemapper
import net.fabricmc.tinyremapper.TinyRemapperMappingsHelper
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.jar.JarFile

class ForkModLoader {
    private val mods = mutableListOf<ForkMod>()

    fun loadMods() {
        val modsDir = File("mods")
        if (!modsDir.exists()) modsDir.mkdirs()

        MinecraftVersionManager.detectVersion()
        val detectedVersion = MinecraftVersionManager.mcVersion

        val mappingsFile = MinecraftVersionManager.getMappingsFile()
        val remapper = TinyRemapper.newRemapper()
            .withMappings(TinyRemapperMappingsHelper.create(mappingsFile.toPath(), "official", "intermediary"))
            .renameInvalidLocals(true)
            .build()

        modsDir.listFiles { file -> file.extension == "jar" }?.forEach { file ->
            if (!ModCompatibilityChecker.isCompatible(file, detectedVersion)) {
                println("[Fork] Skipping incompatible mod: ${file.name}")
                return@forEach
            }

            val tempOutput = Files.createTempFile("remapped_", ".jar")
            remapper.readClassPath(MinecraftVersionManager.mcJar)
            remapper.readInputs(file)
            remapper.apply(file.toPath(), tempOutput)

            val jar = JarFile(tempOutput.toFile())
            val manifest = jar.manifest ?: return@forEach
            val modClass = manifest.mainAttributes.getValue("Fork-Mod-Class") ?: return@forEach

            val classLoader = URLClassLoader(arrayOf(tempOutput.toUri().toURL()), MinecraftVersionManager.mcClassLoader)
            val clazz = classLoader.loadClass(modClass)
            val instance = clazz.getDeclaredConstructor().newInstance() as? ForkModInitializer ?: return@forEach

            val mod = ForkMod(instance, file.name)
            mods.add(mod)
            println("[Fork] Loaded mod: ${mod.name}")
        }

        remapper.finish()
    }

    fun startGame() {
        println("[Fork] Initializing mods...")
        mods.forEach { it.initializer.onInitialize() }
    }
}
