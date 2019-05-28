package com.kotlin.mypractice.knowledgebox.stepone.scenes.showdetail

import com.kotlin.mypractice.knowledgebox.stepone.models.Error
import com.kotlin.mypractice.knowledgebox.stepone.models.News
import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler

object ShowNews {
    object FetchNews {
        class Request
        data class Response(
            val news: News?,
            val newsHandler: NewsHandler,
            val error: Error?
        )

        data class ViewModel(
            val title: String,
            val news: NewsViewModel
        )

        data class ErrorViewModel(
            val title: String,
            val errorMessageRId: Int
        )
    }

    data class NewsViewModel(
        val newsInformation: String?
    )
}
