package com.kotlin.mypractice.knowledgebox.stepone.providers

import com.kotlin.mypractice.knowledgebox.stepone.models.Error
import com.kotlin.mypractice.knowledgebox.stepone.models.News

interface NewsStoreInterface {
    fun fetchNews(newsHandlerId: String, completionHandler: (News?, Error?) -> Unit)
}

class NewsProvider(private val newsStore: NewsStoreInterface) {
    fun fetchNews(newsHandlerId: String, completionHandler: (News?, Error?) -> Unit) {
        newsStore.fetchNews(newsHandlerId, completionHandler)
    }
}
