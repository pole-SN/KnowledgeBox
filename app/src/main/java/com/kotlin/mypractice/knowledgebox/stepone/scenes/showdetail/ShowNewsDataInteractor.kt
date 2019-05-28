package com.kotlin.mypractice.knowledgebox.stepone.scenes.showdetail

import com.kotlin.mypractice.knowledgebox.stepone.externals.NewsHandlerTest
import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler
import com.kotlin.mypractice.knowledgebox.stepone.providers.NewsProvider

interface BusinessLogic {
    fun fetchNews(request: ShowNews.FetchNews.Request)
}

interface DataStore {
    var newsHandler: NewsHandler
}

class ShowNewsDataInteractor : BusinessLogic, DataStore {
    lateinit var mPresenter: PresentationLogic
    private val newsProvider = NewsProvider(
        NewsHandlerTest()
    )

    override lateinit var newsHandler: NewsHandler

    override fun fetchNews(request: ShowNews.FetchNews.Request) {
        val newsHandler = this.newsHandler

        newsProvider.fetchNews(newsHandler.newsHandlerId) { news, error ->
            val response = ShowNews.FetchNews.Response(
                news,
                newsHandler,
                error
            )
            mPresenter.presentNews(response)
        }
    }
}
