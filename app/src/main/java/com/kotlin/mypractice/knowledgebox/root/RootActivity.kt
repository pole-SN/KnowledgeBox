package com.kotlin.mypractice.knowledgebox.root

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.app_bar_main.*


interface RootActivityListener {
    fun onBackPressed()
}

interface RootDisplayLogic

class RootActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mFragmentReplacer: FragmentReplacerInterface

    init {
        val fragmentReplacer = FragmentReplacer()

        this.mFragmentReplacer = fragmentReplacer
        fragmentReplacer.mActivity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlin.mypractice.knowledgebox.R.layout.activity_root)

        setupActionBar()
        mFragmentReplacer.replaceToContentsListFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.kotlin.mypractice.knowledgebox.R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.kotlin.mypractice.knowledgebox.R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.kotlin.mypractice.knowledgebox.R.id.nav_step_one -> {
                mFragmentReplacer.replaceToContentsListFragment()
            }
            com.kotlin.mypractice.knowledgebox.R.id.nav_step_two -> {

            }
            com.kotlin.mypractice.knowledgebox.R.id.nav_step_three -> {

            }
            com.kotlin.mypractice.knowledgebox.R.id.nav_step_four -> {

            }
        }
        layout_nav_drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (layout_nav_drawer.isDrawerOpen(GravityCompat.START)) {
            layout_nav_drawer.closeDrawer(GravityCompat.START)
        } else {
            (supportFragmentManager.fragments.last() as? RootActivityListener)?.run {
                onBackPressed()
            } ?: moveTaskToBack(true)
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(layout_toolbar)

        var inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val toggle = object : ActionBarDrawerToggle(
            this,
            layout_nav_drawer,
            layout_toolbar,
            com.kotlin.mypractice.knowledgebox.R.string.navigation_drawer_open,
            com.kotlin.mypractice.knowledgebox.R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val child: View? = layout_nav_drawer.focusedChild
                child?.clearFocus()

                inputMethodManager!!.hideSoftInputFromWindow(
                    drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                )

                super.onDrawerSlide(drawerView, slideOffset)
            }
        }

        layout_nav_drawer.addDrawerListener(toggle)
        toggle.syncState()
        layout_nav_view.setNavigationItemSelectedListener(this)

        layout_all_delete_button.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}
