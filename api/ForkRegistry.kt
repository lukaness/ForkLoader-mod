// ===============================
// File: api/ForkRegistry.kt
// ===============================

package api

object ForkRegistry {
    private val items = mutableListOf<String>()

    fun registerItem(id: String) {
        items.add(id)
        println("[ForkRegistry] Registered item: $id")
    }

    fun listItems(): List<String> = items
}
