package com.kotlin.mypractice.knowledgebox.stepone.scenes.deletecontent

import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler

object Content {
    object GetContent {
        class Request
        data class Response(
            val newsHandler: NewsHandler
        )

        data class ViewModel(
            val name: String
        )
    }

    object DeleteContent {
        class Request
        class Response
        class ViewModel
    }
}
