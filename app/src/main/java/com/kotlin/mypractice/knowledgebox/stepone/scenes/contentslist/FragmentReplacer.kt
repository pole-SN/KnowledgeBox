package com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontents.CreateContentFragment
import com.kotlin.mypractice.knowledgebox.stepone.scenes.showdetail.ShowNewsFragment


interface FragmentReplacingLogic {
    fun replaceToCreateContentFragment(newsHandlerId: String? = null)
    fun replaceToShowDetailsFragment(newsHandlerId: String)
}

interface DataPassing {
    var dataStore: DataStore
}

interface FragmentReplacerInterface : FragmentReplacingLogic, DataPassing

class FragmentReplacer : FragmentReplacerInterface {
    lateinit var mFragment: ContentsListFragment
    override lateinit var dataStore: DataStore

    override fun replaceToCreateContentFragment(newsHandlerId: String?) {
        val createContentFragment = CreateContentFragment().apply {
            mFragmentReplacer.dataStore.newsHandler =
                dataStore.newsHandlers?.find { it.newsHandlerId == newsHandlerId }
        }

        mFragment.requireFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_base, createContentFragment)
            .commit()
    }

    override fun replaceToShowDetailsFragment(newsHandlerId: String) {
        val newsHandler =
            dataStore.newsHandlers?.find { it.newsHandlerId == newsHandlerId } ?: return

        ShowNewsFragment().apply {
            mFragmentReplacer.dataStore.newsHandler = newsHandler
        }.show(mFragment.fragmentManager, null)
    }
}
