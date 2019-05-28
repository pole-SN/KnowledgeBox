package com.kotlin.mypractice.knowledgebox.stepone.scenes.deletecontent


interface PresentationLogic {
    fun presentNewsHandler(response: Content.GetContent.Response)
    fun presentDeleteNewsHandler(response: Content.DeleteContent.Response)
}

class DeleteContentPresenter : PresentationLogic {
    lateinit var mFragment: DisplayLogic

    override fun presentNewsHandler(response: Content.GetContent.Response) {
        val viewModel = Content.GetContent.ViewModel(response.newsHandler.title)
        mFragment.displayNewsHandler(viewModel)
    }

    override fun presentDeleteNewsHandler(response: Content.DeleteContent.Response) {
        val viewModel = Content.DeleteContent.ViewModel()
        mFragment.displayDeleteNewsHandler(viewModel)
    }
}
