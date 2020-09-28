package dev.weazyexe.firesafety.ui.screen.main.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.ui.base.PaginationableAdapter
import dev.weazyexe.firesafety.utils.ToolbarConfig
import dev.weazyexe.firesafety.utils.extensions.useViewModel
import kotlinx.android.synthetic.main.fragment_terms.*
import kotlinx.android.synthetic.main.toolbar_terms.*
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.utilktx.util.KeyboardUtil

/**
 * Фрагмент со списком терминов
 */
class TermsFragment : Fragment() {

    private lateinit var viewModel: TermsViewModel
    private val termsController = TermsController {
        viewModel.onTermClick(it)
    }

    private val adapter = PaginationableAdapter {
        viewModel.loadMore(terms_search_et.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = useViewModel(this, TermsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terms, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadTerms()
        bind()
    }

    private fun bind() {
        terms_list_rv.adapter = adapter
        terms_list_rv.layoutManager = LinearLayoutManager(requireContext())
        terms_list_rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        terms_toolbar_search_open_btn.setOnClickListener {
            terms_toolbar_close_search_layout.visibility = View.GONE
            terms_toolbar_search_layout.visibility = View.VISIBLE
        }

        terms_search_et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            true
        }

        terms_clear_search_btn.setOnClickListener {
            if (terms_search_et.text.isNotEmpty()) {
                terms_search_et.setText("")
                search()
            } else {
                terms_toolbar_close_search_layout.visibility = View.VISIBLE
                terms_toolbar_search_layout.visibility = View.GONE
            }
        }

        // TODO: сделать шобы тулбар тайтл показывался
        // TODO: чтобы при поиске клавиатура скрывалась
        ToolbarConfig.builder(requireActivity() as AppCompatActivity)
            .fromId(R.id.terms_toolbar)
            .setTitleText(getString(R.string.main_terms))
            .setDisplayShowTitleEnabled(true)
            .apply()

        viewModel.paginationState.observe(this) {
            adapter.setState(it)
        }

        viewModel.terms.observe(this) {
            val count = adapter.itemCount
            adapter.setItems(
                ItemList.create().addAll(it, termsController),
                viewModel.paginationState.value ?: PaginationState.READY
            )

            // в первую загрузку данных список почему-то скроллится вниз, возвращаем
            // 1 потому что loader считается как айтем
            if (count == 1) {
                terms_list_rv.scrollToPosition(0)
            }
        }
    }

    private fun search() {
        viewModel.terms.value?.clear()
        viewModel.loadTerms(terms_search_et.text.toString())
    }
}