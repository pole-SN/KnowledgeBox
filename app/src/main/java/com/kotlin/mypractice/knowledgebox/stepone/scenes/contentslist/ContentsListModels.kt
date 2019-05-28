package com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist

import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler

object ContentsList {
    object FetchContents {
        class Request
        data class Response(
            val newsHandlers: List<NewsHandler>?
        )

        data class ViewModel(
            val totalCount: String,
            val newsHandlers: List<ContentsViewModel>
        )
    }

    data class ContentsViewModel(
        val newsHandlerId: String,
        val iconRId: Int,
        val name: String
    )
}
