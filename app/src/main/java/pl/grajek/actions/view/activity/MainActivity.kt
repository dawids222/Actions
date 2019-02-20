package pl.grajek.actions.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
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
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.view.adapter.ActionAdapter
import pl.grajek.actions.view.dialog.DialogCreator
import pl.grajek.actions.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ActionAdapter
    private val dialogCreator = DialogCreator(this)

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

        adapter = ActionAdapter({
            mainViewModel.gotoActionActivity(it)
        }, {
            showDeleteActionDialog(it)
        })

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
            if (categories?.isEmpty() == true)
                mainViewModel.currentCategory = null

            tabs.removeAllTabs()
            createTabs(categories)
            manageFabVisibility(categories)
        })

        mainViewModel.errorMessage.observe(this, Observer {
            val snackbar = Snackbar.make(mainLayout, it!!, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorError))
            snackbar.show()
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
                    val category = selectedTab.tag as Category
                    showDeleteCategoryDialog(category)
                }
            }
            R.id.action_modify_category -> {
                val selectedTab = tabs.getTabAt(tabs.selectedTabPosition)
                if (selectedTab != null) {
                    val categoryToModify = selectedTab.tag as Category
                    mainViewModel.gotoCategoryEditActivity(categoryToModify)
                }
            }
            R.id.action_show_graph -> {
                mainViewModel.gotoGraphActivity()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun showDeleteCategoryDialog(category: Category) {
        val dialog = dialogCreator.create(R.string.category_delete_title,
            R.string.category_delete_message,
            true, R.string.dialog_ok, {
                mainViewModel.delete(category)
            },
            R.string.dialog_cancel, {})
        dialog.show()
    }

    private fun showDeleteActionDialog(action: Action) {
        val dialog = dialogCreator.create(R.string.action_delete_title,
            R.string.action_delete_message,
            true, R.string.dialog_ok, {
                mainViewModel.delete(action)
            },
            R.string.dialog_cancel, {})
        dialog.show()
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
