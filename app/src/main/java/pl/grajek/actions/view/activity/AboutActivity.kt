package pl.grajek.actions.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import kotlinx.android.synthetic.main.activity_about.*
import pl.grajek.actions.R


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.about_activity_title)


        val description = getString(R.string.about_description)
        val html = Html.fromHtml(description)
        aboutTextView.text = html
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
