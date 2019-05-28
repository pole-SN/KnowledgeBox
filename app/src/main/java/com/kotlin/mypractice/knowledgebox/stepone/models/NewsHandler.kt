package com.kotlin.mypractice.knowledgebox.stepone.models

data class NewsHandler(
    val newsHandlerId: String,
    var title: String,
    var category: Category?
)

enum class Category {
    ANDROID,
    SHOPPING,
    MUSIC,
    PETS,
    TRAVEL;

    companion object {
        fun from(name: String): Category? {
            return Category.values().find { it.name == name }
        }
    }
}
