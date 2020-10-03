package dev.weazyexe.firesafety.ui.screen.term

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.domain.Term
import dev.weazyexe.firesafety.utils.TERM_KEY
import dev.weazyexe.firesafety.utils.ToolbarConfig
import kotlinx.android.synthetic.main.activity_term.*

class TermActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term)

        ToolbarConfig.builder(this)
            .fromId(R.id.term_toolbar)
            .setTitleText(getString(R.string.term))
            .setDisplayHomeAsUpEnabled(true)
            .setDisplayShowTitleEnabled(true)
            .setOnNavigationClickListener { onBackPressed() }
            .apply()

        val term = intent.getParcelableExtra<Term>(TERM_KEY)
        term?.let {
            term_title_tv.text = it.title
            term_definition_tv.text = it.definition
            term_link_tv.text = it.link
            term_favorite_btn.setText(
                if (it.isFavorite) {
                    R.string.remove_from_favorites
                } else {
                    R.string.add_to_favorites
                }
            )
        }
    }
}