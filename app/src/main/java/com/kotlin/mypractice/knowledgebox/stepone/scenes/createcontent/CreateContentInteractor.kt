package com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontent

import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler
import com.kotlin.mypractice.knowledgebox.stepone.providers.NewsHandlerProvider
import com.kotlin.mypractice.knowledgebox.stepone.services.NewsHandlerRealmStore
import java.util.*

interface BusinessLogic {
    fun getNewsHandler(request: CreateContent.GetNewsHandler.Request)
    fun updateNewsHandler(request: CreateContent.UpdateNewsHandler.Request)
}

interface DataStore {
    var newsHandler: NewsHandler?
}

class CreateContentInteractor : BusinessLogic, DataStore {
    lateinit var mPresenter: PresentationLogic
    private val newsHandlerProvider = NewsHandlerProvider(NewsHandlerRealmStore())
    override var newsHandler: NewsHandler? = null

    override fun getNewsHandler(request: CreateContent.GetNewsHandler.Request) {
        val response = CreateContent.GetNewsHandler.Response(this.newsHandler)
        mPresenter.presentNewsHandler(response)
    }

    override fun updateNewsHandler(request: CreateContent.UpdateNewsHandler.Request) {
        val name = request.contents.name
        val category = request.contents.category
        val newsHandler = this.newsHandler?.let {
            it.apply {
                this.title = name
                this.category = category
            }
        } ?: NewsHandler(
            UUID.randomUUID().toString(),
            name,
            category
        )

        newsHandlerProvider.updateNewsHandler(newsHandler)

        val response = CreateContent.UpdateNewsHandler.Response()
        mPresenter.presentUpdateNewsHandler(response)
    }
}
