package com.kotlin.mypractice.knowledgebox.stepone.scenes.deletecontent

import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler
import com.kotlin.mypractice.knowledgebox.stepone.providers.NewsHandlerProvider
import com.kotlin.mypractice.knowledgebox.stepone.services.NewsHandlerRealmStore

interface BusinessLogic {
    fun getNewsHandler(request: Content.GetContent.Request)
    fun deleteNewsHandler(request: Content.DeleteContent.Request)
}

interface DataStore {
    var newsHandler: NewsHandler
}

class DeleteContentDataInteractor : BusinessLogic, DataStore {
    lateinit var mPresenter: PresentationLogic
    private val newsHandlerProvider = NewsHandlerProvider(NewsHandlerRealmStore())
    override lateinit var newsHandler: NewsHandler

    override fun getNewsHandler(request: Content.GetContent.Request) {
        val response = Content.GetContent.Response(newsHandler)
        mPresenter.presentNewsHandler(response)
    }

    override fun deleteNewsHandler(request: Content.DeleteContent.Request) {
        newsHandlerProvider.deleteNewsHandler(newsHandler.newsHandlerId)

        val response = Content.DeleteContent.Response()
        mPresenter.presentDeleteNewsHandler(response)
    }
}
