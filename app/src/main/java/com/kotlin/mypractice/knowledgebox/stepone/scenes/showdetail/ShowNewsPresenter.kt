package com.kotlin.mypractice.knowledgebox.stepone.scenes.showdetail

import com.kotlin.mypractice.knowledgebox.R

interface PresentationLogic {
    fun presentNews(response: ShowNews.FetchNews.Response)
}

class ShowNewsPresenter : PresentationLogic {
    lateinit var mFragment: DisplayLogic

    override fun presentNews(response: ShowNews.FetchNews.Response) {
        val title = response.newsHandler.title

        response.error?.let {
            val viewModel = ShowNews.FetchNews.ErrorViewModel(
                title,
                R.string.message_error_get_news
            )
            mFragment.displayNewsError(viewModel)
        } ?: run {
            val news = response.news
            val newsViewModel = ShowNews.NewsViewModel(news?.newsInformation)
            val viewModel = ShowNews.FetchNews.ViewModel(
                title,
                newsViewModel
            )
            mFragment.displayNews(viewModel)
        }
    }
}
