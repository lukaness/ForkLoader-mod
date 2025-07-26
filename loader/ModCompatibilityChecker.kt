// ===============================
// File: loader/ModCompatibilityChecker.kt
// ===============================

package loader

import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.util.jar.JarFile

object ModCompatibilityChecker {
    fun isCompatible(modJar: File, targetVersion: String): Boolean {
        val jar = JarFile(modJar)
        val entry = jar.getEntry("META-INF/mod.fork.json") ?: return false

        val stream: InputStream = jar.getInputStream(entry)
        val json = JSONObject(stream.reader().readText())
        val supportedVersions = json.optJSONArray("mcVersions") ?: return false

        for (i in 0 until supportedVersions.length()) {
            if (supportedVersions.getString(i) == targetVersion) return true
        }
        return false
    }
}
