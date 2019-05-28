package com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mypractice.knowledgebox.R

interface DisplayLogic {
    fun displayNewsHandlers(viewModel: ContentsList.FetchContents.ViewModel)
}

class ContentsListFragment : Fragment(), DisplayLogic {
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
        mTotalTextView = view.findViewById(R.id.text_view_total) as TextView

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

    inner class NewsHandlerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryImageView: ImageView = view.findViewById(R.id.image_news_type) as ImageView
        var nameTextView: TextView = view.findViewById(R.id.text_view_name) as TextView
        var editButton: Button = (view.findViewById(R.id.button_edit) as Button)
        var showButton: Button = (view.findViewById(R.id.button_show) as Button)
    }

    inner class NewsHandlerAdapter(private val newsHandlers: List<ContentsList.ContentsViewModel>) :
        RecyclerView.Adapter<NewsHandlerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHandlerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_content, parent, false)

            return NewsHandlerViewHolder(view)
        }

        override fun onBindViewHolder(holder: NewsHandlerViewHolder, position: Int) {
            val newsHandler = newsHandlers[position]

            holder.run {
                categoryImageView.setImageResource(newsHandler.iconRId)
                nameTextView.text = newsHandler.name

                editButton.setOnClickListener {
                    mFragmentReplacer.replaceToCreateContentFragment(newsHandler.newsHandlerId)
                }

                showButton.setOnClickListener {
                    mFragmentReplacer.replaceToShowDetailsFragment(newsHandler.newsHandlerId)
                }
            }
        }

        override fun getItemCount(): Int {
            return newsHandlers.size
        }
    }
}
