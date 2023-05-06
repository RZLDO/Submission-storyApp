package com.example.intermediatedua

import com.example.intermediatedua.data.local.room.StoryResponseItems

object DataDummy {
    fun generateDummyQuoteResponse(): List<StoryResponseItems> {
        val items: MutableList<StoryResponseItems> = arrayListOf()
        for (i in 0..100) {
            val story = StoryResponseItems(
                i.toString(),
                "created at + $i",
                "name $i",
                "description $i",
                i.toDouble(),
                "id : $i",
                i.toDouble(),
            )
            items.add(story)
        }
        return items
    }
}