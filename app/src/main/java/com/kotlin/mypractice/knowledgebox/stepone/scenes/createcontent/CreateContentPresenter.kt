package com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontent

import android.view.View
import com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontents.DisplayLogic

interface PresentationLogic {
    fun presentNewsHandler(response: CreateContent.GetNewsHandler.Response)
    fun presentUpdateNewsHandler(response: CreateContent.UpdateNewsHandler.Response)
}

class CreateContentPresenter : PresentationLogic {
    lateinit var mFragment: DisplayLogic

    override fun presentNewsHandler(response: CreateContent.GetNewsHandler.Response) {
        val newsHandler = response.newsHandler
        val newsHandlerViewModel = CreateContent.ContentsViewModel(
            newsHandler?.title ?: "",
            newsHandler?.category
        )
        val deleteButtonVisibility = newsHandler?.let { View.VISIBLE } ?: View.GONE
        val viewModel = CreateContent.GetNewsHandler.ViewModel(
            newsHandlerViewModel,
            deleteButtonVisibility
        )

        mFragment.displayNewsHandler(viewModel)
    }

    override fun presentUpdateNewsHandler(response: CreateContent.UpdateNewsHandler.Response) {
        val viewModel = CreateContent.UpdateNewsHandler.ViewModel()
        mFragment.displayUpdateNewsHandler(viewModel)
    }
}
