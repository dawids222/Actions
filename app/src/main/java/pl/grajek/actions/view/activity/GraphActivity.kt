package pl.grajek.actions.view.activity

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_graph.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityGraphBinding
import pl.grajek.actions.model.ChartDrawer
import pl.grajek.actions.model.PictureManager
import pl.grajek.actions.model.SnackbarProvider
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.view.animator.FadeAnimator
import pl.grajek.actions.viewmodel.GraphViewModel


class GraphActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY = "CATEGORY"
    }

    private lateinit var graphViewModel: GraphViewModel
    private lateinit var chartDrawer: ChartDrawer
    private lateinit var category: Category

    private val pictureManager = PictureManager()
    private val fadeAnimator = FadeAnimator()
    private val snackbarProvider = SnackbarProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGraphBinding>(this, R.layout.activity_graph)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        graphViewModel = ViewModelProviders.of(this).get(GraphViewModel::class.java)
        binding.vm = graphViewModel

        chartDrawer = ChartDrawer(chart, this)

        handleBundle()
        setTitle()
        setObservers()
    }

    private fun handleBundle() {
        if (intent.hasExtra(CATEGORY)) {
            category = intent.getSerializableExtra(CATEGORY) as Category
        }
    }

    private fun setTitle() {
        title = "${category.name} [${category.unit}]"
    }

    private fun setObservers() {
        graphViewModel.select(category.id!!).observe(this, Observer {
            chartDrawer.draw(it!!)
        })
    }

    private fun zoomOut() {
        chart.fitScreen()
    }

    private fun saveGraph() {
        Dexter
            .withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(getPermissionListener())
            .check()
    }

    private fun getPermissionListener(): PermissionListener {
        return object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                fadeAnimator
                    .setOnEndListener { showSuccessSnackbar() }
                    .animate(flashLayout, 2000)

                val bitmap = pictureManager.getBitmap(chart)
                pictureManager.save(this@GraphActivity, bitmap)
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                showPermissionDeniedSnackbar()
            }
        }
    }

    private fun showSuccessSnackbar() {
        snackbarProvider.create(
            graphLayout,
            R.string.picture_save_success, true,
            ContextCompat.getColor(this@GraphActivity, R.color.colorSuccess)
        ).show()
    }

    private fun showPermissionDeniedSnackbar() {
        snackbarProvider.create(
            graphLayout,
            R.string.external_storage_access_denied, true,
            ContextCompat.getColor(this@GraphActivity, R.color.colorError)
        ).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.graph, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.zoom_out -> zoomOut()
            R.id.save_graph -> saveGraph()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}
