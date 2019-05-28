package com.kotlin.mypractice.knowledgebox.root

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist.ContentsListFragment

interface RootRoutingLogic {
    fun replaceToContentsListFragment()
}

interface RootDataPassing {
    var rootDataStore: RootDataStore
}

interface FragmentReplacerInterface : RootRoutingLogic, RootDataPassing

class FragmentReplacer : FragmentReplacerInterface {
    lateinit var mActivity: RootActivity
    override lateinit var rootDataStore: RootDataStore

    override fun replaceToContentsListFragment() {
        val fragment = ContentsListFragment()

        mActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_base, fragment)
            .commit()
    }
}
