package pl.grajek.actions.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityMainBinding
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.view.adapter.ActionAdapter
import pl.grajek.actions.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ActionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupRecyclerView()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.vm = mainViewModel

        setListeners()
        setObservers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        actionsRecycler.layoutManager = layoutManager

        adapter = ActionAdapter {
            mainViewModel.gotoActionEditActivity(it)
        }

        actionsRecycler.adapter = adapter
    }

    private fun setListeners() {
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val currentCategory = tab.tag as Category
                mainViewModel.currentCategory = currentCategory
                mainViewModel.observeActions(
                    this@MainActivity,
                    currentCategory.id!!,
                    Observer {
                        adapter.setActions(it!!)
                    })
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        fab.setOnClickListener {
            mainViewModel.gotoActionActivity()
        }
    }

    private fun setObservers() {
        mainViewModel.activityToStart.observe(this, Observer {
            if (it != null) {
                val intent = Intent(this, it.type)
                intent.putExtras(it.bundle)
                startActivity(intent)
            }
        })

        mainViewModel.selectCategories().observe(this, Observer { categories ->
            tabs.removeAllTabs()
            createTabs(categories)
            manageFabVisibility(categories)
        })
    }

    private fun createTabs(categories: MutableList<Category>?) {
        categories?.forEach { category ->
            val newTab = tabs.newTab()
            newTab.let { tab ->
                tab.text = category.name
                tab.tag = category
            }
            tabs.addTab(newTab)
        }
    }

    private fun manageFabVisibility(categories: MutableList<Category>?) {
        if (categories?.isEmpty() == true) {
            fab.hide()
        } else {
            fab.show()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_category -> {
                val selectedTab = tabs.getTabAt(tabs.selectedTabPosition)
                if (selectedTab != null) {
                    val categoryToDelete = selectedTab.tag as Category
                    mainViewModel.delete(categoryToDelete)
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
