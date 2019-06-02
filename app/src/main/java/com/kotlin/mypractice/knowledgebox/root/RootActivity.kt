package com.kotlin.mypractice.knowledgebox.root

import android.annotation.SuppressLint
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
import com.kotlin.mypractice.knowledgebox.R
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.app_bar_main.*

interface RootActivityListener {
    fun onBackPressed()
}

interface RootFragment

class RootActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mFragmentReplacer: FragmentReplacerInterface

    init {
        val fragmentReplacer = FragmentReplacer()

        this.mFragmentReplacer = fragmentReplacer
        fragmentReplacer.mActivity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        setupActionBar()
        mFragmentReplacer.replaceToContentsListFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_step_one -> {
                layout_all_delete_button.visibility = View.VISIBLE
                mFragmentReplacer.replaceToContentsListFragment()
            }
            R.id.nav_step_two -> {
                layout_all_delete_button.visibility = View.GONE
                mFragmentReplacer.replaceToImageViewerFragment()
            }
            R.id.nav_step_three -> {

            }
            R.id.nav_step_four -> {

            }
        }
        layout_nav_drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (layout_nav_drawer.isDrawerVisible(GravityCompat.START)) {
            layout_nav_drawer.closeDrawer(GravityCompat.START)
        } else {
            var fragment = supportFragmentManager.fragments.last()
            (fragment as? RootFragment)?.run { super.onBackPressed() }
            (fragment as? RootActivityListener)?.run { onBackPressed() } ?: moveTaskToBack(true)
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
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                layout_nav_drawer.focusedChild?.clearFocus()
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
