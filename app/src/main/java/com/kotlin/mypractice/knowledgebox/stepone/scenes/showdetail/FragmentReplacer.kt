package com.kotlin.mypractice.knowledgebox.stepone.scenes.showdetail

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist.ContentsListFragment

interface RoutingLogic {
    fun replaceToContentsListFragment()
}

interface DataPassing {
    var dataStore: DataStore
}

interface FragmentReplacerInterface : RoutingLogic, DataPassing

class FragmentReplacer : FragmentReplacerInterface {
    lateinit var mFragment: ShowNewsFragment
    override lateinit var dataStore: DataStore

    override fun replaceToContentsListFragment() {
        val contentsListFragment = ContentsListFragment()

        mFragment.requireFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_base, contentsListFragment)
            .commit()
    }
}
