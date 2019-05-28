package com.kotlin.mypractice.knowledgebox.stepone.scenes.deletecontent

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kotlin.mypractice.knowledgebox.R

interface DisplayLogic {
    fun displayNewsHandler(viewModel: Content.GetContent.ViewModel)
    fun displayDeleteNewsHandler(viewModel: Content.DeleteContent.ViewModel)
}

class DeleteContentFragment : DialogFragment(), DisplayLogic {
    private lateinit var mInteractor: BusinessLogic
    lateinit var mFragmentReplacer: FragmentReplacerInterface

    init {
        setup()
    }

    private fun setup() {
        val interactor = DeleteContentDataInteractor()
        val presenter = DeleteContentPresenter()
        val router = FragmentReplacer()

        this.mInteractor = interactor
        this.mFragmentReplacer = router
        interactor.mPresenter = presenter
        presenter.mFragment = this
        router.mFragment = this
        router.dataStore = interactor
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false

        return AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.ok) { _, _ ->
                deleteNewsHandler()
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getNewsHandler()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getNewsHandler() {
        val request = Content.GetContent.Request()
        mInteractor.getNewsHandler(request)
    }

    override fun displayNewsHandler(viewModel: Content.GetContent.ViewModel) {
        (this.dialog as AlertDialog).setMessage(
            getString(
                R.string.message_confirm_delete_newshandler,
                viewModel.name
            )
        )
    }

    private fun deleteNewsHandler() {
        val request = Content.DeleteContent.Request()
        mInteractor.deleteNewsHandler(request)
    }

    override fun displayDeleteNewsHandler(viewModel: Content.DeleteContent.ViewModel) {
        mFragmentReplacer.replaceToContentsListFragment()
    }
}
