package com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontents

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.root.RootActivityListener
import com.kotlin.mypractice.knowledgebox.stepone.models.Category
import com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontent.*
import kotlinx.android.synthetic.main.fragment_create_newshandler.view.*


interface DisplayLogic {
    fun displayNewsHandler(viewModel: CreateContent.GetNewsHandler.ViewModel)
    fun displayUpdateNewsHandler(viewModel: CreateContent.UpdateNewsHandler.ViewModel)
}

class CreateContentFragment : Fragment(), DisplayLogic, RootActivityListener,
    CreateNewsHandlerSavedDialogFragment.Listener {
    private lateinit var mInteractor: BusinessLogic
    lateinit var mFragmentReplacer: FragmentReplacerInterface

    private lateinit var mDeleteButton: Button
    private lateinit var mNameEditText: EditText
    private lateinit var mCategoryRadioGroup: RadioGroup

    enum class CategoryRadioButtonRId(val category: Category, val rId: Int) {
        ANDROID(Category.ANDROID, R.id.radio_button_android),
        SHOPPING(Category.SHOPPING, R.id.radio_button_shopping),
        MUSIC(Category.MUSIC, R.id.radio_button_music),
        PETS(Category.PETS, R.id.radio_button_pets),
        TRAVEL(Category.TRAVEL, R.id.radio_button_travel);

        companion object {
            fun from(category: Category): CategoryRadioButtonRId? {
                return values().find { it.category == category }
            }

            fun from(rId: Int): CategoryRadioButtonRId? {
                return values().find { it.rId == rId }
            }
        }
    }

    init {
        setup()
    }

    private fun setup() {
        val interactor = CreateContentInteractor()
        val presenter = CreateContentPresenter()
        val fragmentReplacer = FragmentReplacer()

        this.mInteractor = interactor
        this.mFragmentReplacer = fragmentReplacer
        interactor.mPresenter = presenter
        presenter.mFragment = this
        fragmentReplacer.mFragment = this
        fragmentReplacer.dataStore = interactor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_create_newshandler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        getNewsHandler()
    }

    private fun setupViews(view: View) {
        mDeleteButton = (view.button_delete as Button).apply {
            setOnClickListener {
                mFragmentReplacer.showDeleteContentDialogFragment()
            }
            visibility = View.GONE
        }

        (view.button_save as Button).run {
            setOnClickListener {
                updateNewsHandler()
            }
        }

        mNameEditText = view.edit_text_title
        mCategoryRadioGroup = view.radio_group_category
    }

    override fun onBackPressed() {
        mFragmentReplacer.replaceToContentsListFragment()
    }

    override fun onDetach() {
        var inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(
            view?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
        super.onDetach()
    }

    private fun getNewsHandler() {
        val request = CreateContent.GetNewsHandler.Request()
        mInteractor.getNewsHandler(request)
    }

    override fun displayNewsHandler(viewModel: CreateContent.GetNewsHandler.ViewModel) {
        val newsHandler = viewModel.contents

        mNameEditText.setText(newsHandler.name, TextView.BufferType.EDITABLE)

        newsHandler.category?.let {
            CategoryRadioButtonRId.from(it)?.let {
                mCategoryRadioGroup.check(it.rId)
            }
        }

        mDeleteButton.visibility = viewModel.deleteButtonVisibility
    }

    private fun updateNewsHandler() {
        val name = mNameEditText.text.toString()
        val category =
            CategoryRadioButtonRId.from(mCategoryRadioGroup.checkedRadioButtonId)?.category
        val newsHandler = CreateContent.ContentsViewModel(
            name,
            category
        )
        val request = CreateContent.UpdateNewsHandler.Request(newsHandler)

        mInteractor.updateNewsHandler(request)
    }

    override fun displayUpdateNewsHandler(viewModel: CreateContent.UpdateNewsHandler.ViewModel) {
        CreateNewsHandlerSavedDialogFragment.show(requireFragmentManager(), this)
    }

    override fun onSavedDialogOkClick() {
        mFragmentReplacer.replaceToContentsListFragment()
    }
}

class CreateNewsHandlerSavedDialogFragment : DialogFragment() {
    interface Listener {
        fun onSavedDialogOkClick()
    }

    companion object {
        fun show(fragmentManager: FragmentManager, listener: Listener) {
            CreateNewsHandlerSavedDialogFragment().apply {
                this.listener = listener
            }.show(fragmentManager, null)
        }
    }

    private lateinit var listener: Listener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false

        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.message_save_success)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onSavedDialogOkClick()
            }
            .create()
    }
}
