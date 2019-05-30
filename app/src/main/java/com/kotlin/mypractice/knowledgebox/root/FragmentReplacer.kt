package com.kotlin.mypractice.knowledgebox.root

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist.ContentsListFragment

interface RootRoutingLogic {
    fun replaceToContentsListFragment()
}

interface FragmentReplacerInterface : RootRoutingLogic

class FragmentReplacer : FragmentReplacerInterface {
    lateinit var mActivity: RootActivity

    override fun replaceToContentsListFragment() {
        val fragment = ContentsListFragment()

        mActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_base, fragment)
            .commit()
    }
}
