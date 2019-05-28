package com.kotlin.mypractice.knowledgebox.stepone.scenes.showdetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kotlin.mypractice.knowledgebox.R

interface DisplayLogic {
    fun displayNews(viewModel: ShowNews.FetchNews.ViewModel)
    fun displayNewsError(viewModel: ShowNews.FetchNews.ErrorViewModel)
}

class ShowNewsFragment : DialogFragment(), DisplayLogic {
    private lateinit var mInteractor: BusinessLogic
    lateinit var mFragmentReplacer: FragmentReplacerInterface

    init {
        setup()
    }

    private fun setup() {
        val interactor = ShowNewsDataInteractor()
        val presenter = ShowNewsPresenter()
        val fragmentReplacer = FragmentReplacer()

        this.mInteractor = interactor
        this.mFragmentReplacer = fragmentReplacer
        interactor.mPresenter = presenter
        presenter.mFragment = this
        fragmentReplacer.mFragment = this
        fragmentReplacer.dataStore = interactor
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false

        return AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.ok) { _, _ ->
                mFragmentReplacer.replaceToContentsListFragment()
            }
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val request = ShowNews.FetchNews.Request()
        mInteractor.fetchNews(request)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun displayNews(viewModel: ShowNews.FetchNews.ViewModel) {
        (this.dialog as AlertDialog).setTitle(viewModel.title)
        viewModel.news.let {
            (this.dialog as AlertDialog).setMessage(it.newsInformation)
        }
    }

    override fun displayNewsError(viewModel: ShowNews.FetchNews.ErrorViewModel) {
        (this.dialog as AlertDialog).setTitle(viewModel.title)
        (this.dialog as AlertDialog).setMessage("Error")
        Toast.makeText(context, viewModel.errorMessageRId, Toast.LENGTH_LONG).show()
    }
}