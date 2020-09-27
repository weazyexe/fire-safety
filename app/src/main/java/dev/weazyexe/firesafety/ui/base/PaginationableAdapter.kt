package dev.weazyexe.firesafety.ui.base

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.weazyexe.firesafety.R
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.utilktx.ktx.ui.context.getDisplayMetrics

/**
 * Класс адаптера с поддержкой пагинации на основе EasyAdapter
 */
class PaginationableAdapter(
    paginationFooterController: BasePaginationFooterController<*>? = PaginationFooterItemController(),
    onShowMoreListener: () -> Unit = {}
) : EasyPaginationAdapter(paginationFooterController, onShowMoreListener) {

    init {
        setOnShowMoreListener(onShowMoreListener)
    }

    private class PaginationFooterItemController :
        BasePaginationFooterController<PaginationFooterItemController.Holder>() {

        override fun createViewHolder(parent: ViewGroup, listener: OnShowMoreListener): Holder {
            return Holder(parent, listener)
        }

        inner class Holder(parent: ViewGroup, listener: OnShowMoreListener) :
            EasyPaginationAdapter.BasePaginationFooterHolder(parent, R.layout.layout_pagination_footer) {

            val screenWidth = itemView.context.getDisplayMetrics().widthPixels
            val loadingIndicator: ProgressBar = itemView.findViewById(R.id.pagination_progress_bar)
            val showMoreTv: Button = itemView.findViewById(R.id.pagination_try_again_btn)

            init {
                showMoreTv.setOnClickListener { listener.onShowMore() }
                loadingIndicator.visibility = View.GONE
                showMoreTv.visibility = View.GONE
            }

            override fun bind(state: PaginationState) {

                //для пагинации на StaggeredGrid
                if (itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                    itemView.updateLayoutParams<StaggeredGridLayoutManager.LayoutParams> {
                        isFullSpan = true
                        width = screenWidth
                    }
                }

                loadingIndicator.isVisible = state == PaginationState.READY
                showMoreTv.isVisible = state == PaginationState.ERROR
            }
        }
    }
}