package dev.weazyexe.firesafety.ui.screen.main.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.domain.Document
import dev.weazyexe.firesafety.ui.base.PaginationableAdapter
import dev.weazyexe.firesafety.utils.extensions.useViewModel
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.toolbar_main.*
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.utilktx.util.KeyboardUtil

class LibraryFragment : Fragment() {

    private lateinit var viewModel: LibraryViewModel

    private val libraryController = LibraryController {
        openLink(it)
    }

    private val adapter = PaginationableAdapter {
        viewModel.loadMore(main_search_et.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = useViewModel(this, LibraryViewModel::class.java)

        val inputStream = activity?.assets?.open("library.csv")
        inputStream?.let {
            viewModel.fillDatabaseIfNeedAndGetData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onStart() {
        super.onStart()
        bind()
    }

    private fun bind() {
        documents_list_rv.adapter = adapter
        documents_list_rv.layoutManager = LinearLayoutManager(requireContext())
        documents_list_rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        main_search_et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardUtil.hideSoftKeyboard(requireActivity())
                viewModel.search(main_search_et.text.toString())
            }
            true
        }

        viewModel.paginationState.observe(this) {
            adapter.setState(it)
        }

        viewModel.documents.observe(this) {
            val count = adapter.itemCount
            adapter.setItems(
                ItemList.create().addAll(it, libraryController),
                viewModel.paginationState.value ?: PaginationState.READY
            )

            // в первую загрузку данных список почему-то скроллится вниз, возвращаем
            // 1 потому что loader считается как айтем
            if (count == 1) {
                documents_list_rv.scrollToPosition(0)
            }
        }
    }

    private fun openLink(document: Document) {
        // TODO
    }
}