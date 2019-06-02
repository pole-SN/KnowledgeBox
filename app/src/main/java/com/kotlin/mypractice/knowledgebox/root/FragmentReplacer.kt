package com.kotlin.mypractice.knowledgebox.root

import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.stepone.scenes.contentslist.ContentsListFragment
import com.kotlin.mypractice.knowledgebox.steptwo.scenes.imageviewer.ImageViewerFragment

interface RootRoutingLogic {
    fun replaceToContentsListFragment()
    fun replaceToImageViewerFragment()
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

    override fun replaceToImageViewerFragment() {
        val fragment = ImageViewerFragment()

        mActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_base, fragment)
            .commit()
    }
}
