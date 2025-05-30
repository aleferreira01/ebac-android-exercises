package br.com.alexander.awesomemovieapp.placeholder

import br.com.alexander.awesomemovieapp.R
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    private val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPlaceholderItem(position: Int): PlaceholderItem {
        return PlaceholderItem(
            position.toString(),
            "Batman",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam luctus urna erat, " +
                    "ut laoreet metus vehicula malesuada. Fusce purus sapien, co",
            R.drawable.movie_cover
        )
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(
        val id: String,
        val movieNameExample: String,
        val movieDescExample: String,
        val coverExample: Int
    ) {
        //override fun toString(): String = content
    }
}