package dev.weazyexe.firesafety.ui.screen.main.library

import android.view.ViewGroup
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.domain.Document
import kotlinx.android.synthetic.main.view_document.view.*
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Контроллер документа для [EasyAdapter]
 */
class LibraryController(private val onItemClick: (Document) -> Unit = {}) :
    BindableItemController<Document, LibraryController.Holder>() {

    override fun createViewHolder(parent: ViewGroup?): Holder = Holder(parent)

    override fun getItemId(data: Document?): Any = data?.id ?: data.hashCode()

    inner class Holder(parent: ViewGroup?) :
        BindableViewHolder<Document>(parent, R.layout.view_document) {

        override fun bind(document: Document) {
            val (id, title, link) = document
            itemView.document_title_tv.text = title
            itemView.document_link_tv.text = link

            itemView.document_layout.setOnClickListener { onItemClick(document) }
        }
    }
}