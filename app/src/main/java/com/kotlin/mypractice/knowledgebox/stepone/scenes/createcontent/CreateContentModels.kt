package com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontent

import com.kotlin.mypractice.knowledgebox.stepone.models.Category
import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler

object CreateContent {
    object GetNewsHandler {
        class Request
        data class Response(
            val newsHandler: NewsHandler?
        )

        data class ViewModel(
            val contents: ContentsViewModel,
            val deleteButtonVisibility: Int
        )
    }

    object UpdateNewsHandler {
        data class Request(
            val contents: ContentsViewModel
        )

        class Response
        class ViewModel
    }

    data class ContentsViewModel(
        val name: String,
        val category: Category?
    )
}
