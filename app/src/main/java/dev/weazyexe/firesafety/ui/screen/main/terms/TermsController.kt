package dev.weazyexe.firesafety.ui.screen.main.terms

import android.view.ViewGroup
import androidx.core.view.isVisible
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.domain.Term
import kotlinx.android.synthetic.main.view_term.view.*
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Контроллер термина для [EasyAdapter]
 */
class TermsController(private val onItemClick: (Term) -> Unit = {}): BindableItemController<Term, TermsController.Holder>() {

    override fun createViewHolder(parent: ViewGroup?): Holder = Holder(parent)

    override fun getItemId(data: Term?): Any = data?.id ?: data.hashCode()

    inner class Holder(parent: ViewGroup?) : BindableViewHolder<Term>(parent, R.layout.view_term) {

        override fun bind(term: Term) {
            val (_, title, definition, link, isFavorite) = term
            itemView.term_title_tv.text = title
            itemView.term_definition_tv.text = definition
            itemView.term_link_tv.text = link
            itemView.term_favorite.isVisible = isFavorite

            itemView.term_layout.setOnClickListener { onItemClick(term) }
        }
    }
}