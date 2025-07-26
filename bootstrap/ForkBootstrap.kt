// ===============================
// File: bootstrap/ForkBootstrap.kt
// ===============================

package bootstrap

import loader.ForkModLoader

object ForkBootstrap {
    @JvmStatic
    fun main(args: Array<String>) {
        println("[Fork] Bootstrapping Fork ModLoader...")
        val loader = ForkModLoader()
        loader.loadMods()
        loader.startGame()
    }
}
