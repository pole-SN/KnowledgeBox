package com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontent

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist.ContentsListFragment
import com.kotlin.mypractice.knowledgebox.stepone.scenes.createcontents.CreateContentFragment
import com.kotlin.mypractice.knowledgebox.stepone.scenes.deletecontent.DeleteContentFragment

interface RoutingLogic {
    fun replaceToContentsListFragment()
    fun showDeleteContentDialogFragment()
}

interface DataPassing {
    var dataStore: DataStore
}

interface FragmentReplacerInterface : RoutingLogic, DataPassing

class FragmentReplacer : FragmentReplacerInterface {
    lateinit var mFragment: CreateContentFragment
    override lateinit var dataStore: DataStore

    override fun replaceToContentsListFragment() {
        val contentsListFragment = ContentsListFragment()

        mFragment.requireFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_base, contentsListFragment)
            .commit()
    }

    override fun showDeleteContentDialogFragment() {
        val newsHandler = dataStore.newsHandler ?: return

        DeleteContentFragment().apply {
            mFragmentReplacer.dataStore.newsHandler = newsHandler
        }.show(mFragment.fragmentManager, null)
    }
}
