package com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.root.RootFragment
import kotlinx.android.synthetic.main.fragment_contents_list.view.*
import kotlinx.android.synthetic.main.list_item_content.view.*

interface DisplayLogic {
    fun displayNewsHandlers(viewModel: ContentsList.FetchContents.ViewModel)
}

class ContentsListFragment : Fragment(), DisplayLogic, RootFragment {
    private lateinit var mInteractor: BusinessLogic
    private lateinit var mFragmentReplacer: FragmentReplacerInterface

    private lateinit var mTotalTextView: TextView
    private lateinit var mListView: RecyclerView

    init {
        setup()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_contents_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)

        fetchNewsHandlers()
    }

    private fun setup() {
        val interactor = ContentsListDataInteractor()
        val presenter = ContentsListPresenter()
        val replacer = FragmentReplacer()

        this.mInteractor = interactor
        this.mFragmentReplacer = replacer
        interactor.mPresenter = presenter
        presenter.mFragment = this
        replacer.mFragment = this
        replacer.dataStore = interactor
    }

    private fun setupViews(view: View) {
        mTotalTextView = view.text_view_total

        (view.findViewById(R.id.button_add) as Button).run {
            setOnClickListener {
                mFragmentReplacer.replaceToCreateContentFragment()
            }
        }

        mListView = (view.findViewById(R.id.recycler_view) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun fetchNewsHandlers() {
        val request = ContentsList.FetchContents.Request()
        mInteractor.fetchNewsHandlers(request)
    }

    override fun displayNewsHandlers(viewModel: ContentsList.FetchContents.ViewModel) {
        mTotalTextView.text = viewModel.totalCount
        mListView.adapter = NewsHandlerAdapter(viewModel.newsHandlers)
    }

    inner class NewsHandlerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class NewsHandlerAdapter(private val newsHandlers: List<ContentsList.ContentsViewModel>) :
        RecyclerView.Adapter<NewsHandlerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHandlerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_content, parent, false)

            return NewsHandlerViewHolder(view)
        }

        override fun onBindViewHolder(holder: NewsHandlerViewHolder, position: Int) {
            val newsHandler = newsHandlers[position]
            val view: View = holder.itemView

            holder.run {
                animateAlpha(view.layout_list_item)
                view.image_news_type.setImageResource(newsHandler.iconRId)
                view.text_view_name.text = newsHandler.name

                view.button_edit.setOnClickListener {
                    mFragmentReplacer.replaceToCreateContentFragment(newsHandler.newsHandlerId)
                }

                view.button_show.setOnClickListener {
                    mFragmentReplacer.replaceToShowDetailsFragment(newsHandler.newsHandlerId)
                }
            }
        }

        private fun animateAlpha(target: View) {
            val objectAnimator = ObjectAnimator.ofFloat( target, "alpha", 0f, 1f )
            objectAnimator.setDuration(500)
            objectAnimator.start()
        }

        override fun getItemCount(): Int {
            return newsHandlers.size
        }
    }
}
