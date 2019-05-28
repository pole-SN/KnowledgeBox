package com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist

import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler
import com.kotlin.mypractice.knowledgebox.stepone.providers.NewsHandlerProvider
import com.kotlin.mypractice.knowledgebox.stepone.services.NewsHandlerRealmStore

interface BusinessLogic {
    fun fetchNewsHandlers(request: ContentsList.FetchContents.Request)
}

interface DataStore {
    var newsHandlers: List<NewsHandler>?
}

class ContentsListDataInteractor : BusinessLogic, DataStore {
    lateinit var mPresenter: PresentationLogic
    private val mNewsHandlerProvider = NewsHandlerProvider(NewsHandlerRealmStore())
    override var newsHandlers: List<NewsHandler>? = null

    override fun fetchNewsHandlers(request: ContentsList.FetchContents.Request) {
        val newsHandlers = mNewsHandlerProvider.fetchNewsHandlers()
        this.newsHandlers = newsHandlers

        val response = ContentsList.FetchContents.Response(newsHandlers)
        mPresenter.presentNewsHandlers(response)
    }
}
