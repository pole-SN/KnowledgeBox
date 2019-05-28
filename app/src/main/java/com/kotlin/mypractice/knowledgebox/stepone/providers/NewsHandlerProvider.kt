package com.kotlin.mypractice.knowledgebox.stepone.providers

import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler

interface NewsHandlerStoreInterface {
    fun fetchNewsHandlers(): List<NewsHandler>?
    fun updateNewsHandler(newsHandler: NewsHandler)
    fun deleteNewsHandler(newsHandlerId: String)
}

class NewsHandlerProvider(private val newsHandlerStore: NewsHandlerStoreInterface) {
    fun fetchNewsHandlers(): List<NewsHandler>? {
        return newsHandlerStore.fetchNewsHandlers()
    }

    fun updateNewsHandler(newsHandler: NewsHandler) {
        newsHandlerStore.updateNewsHandler(newsHandler)
    }

    fun deleteNewsHandler(newsHandlerId: String) {
        newsHandlerStore.deleteNewsHandler(newsHandlerId)
    }
}
