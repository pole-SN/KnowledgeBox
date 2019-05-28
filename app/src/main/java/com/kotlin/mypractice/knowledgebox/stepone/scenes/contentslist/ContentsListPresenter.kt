package com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.models.Category

interface PresentationLogic {
    fun presentNewsHandlers(response: ContentsList.FetchContents.Response)
}

class ContentsListPresenter : PresentationLogic {
    lateinit var mFragment: DisplayLogic

    override fun presentNewsHandlers(response: ContentsList.FetchContents.Response) {
        val newsHandlers = response.newsHandlers?.map {
            val iconRId = when (it.category) {
                Category.ANDROID -> R.drawable.news_android
                Category.SHOPPING -> R.drawable.news_shopping
                Category.MUSIC -> R.drawable.news_music
                Category.PETS -> R.drawable.news_pets
                Category.TRAVEL -> R.drawable.news_travel
                else -> R.drawable.unknown
            }

            ContentsList.ContentsViewModel(
                it.newsHandlerId,
                iconRId,
                it.title
            )
        } ?: listOf()

        val viewModel = ContentsList.FetchContents.ViewModel(
            newsHandlers.size.toString(),
            newsHandlers
        )
        mFragment.displayNewsHandlers(viewModel)
    }
}
