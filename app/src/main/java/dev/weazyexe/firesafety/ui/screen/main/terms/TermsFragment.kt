package dev.weazyexe.firesafety.ui.screen.main.terms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.domain.Term
import dev.weazyexe.firesafety.ui.base.PaginationableAdapter
import dev.weazyexe.firesafety.ui.screen.term.TermActivity
import dev.weazyexe.firesafety.utils.TERM_KEY
import dev.weazyexe.firesafety.utils.extensions.useViewModel
import kotlinx.android.synthetic.main.fragment_terms.*
import kotlinx.android.synthetic.main.toolbar_main.*
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.utilktx.util.KeyboardUtil

/**
 * Фрагмент со списком терминов
 */
class TermsFragment : Fragment() {

    private lateinit var viewModel: TermsViewModel
    private val termsController = TermsController {
        openTermActivity(it)
    }

    private val adapter = PaginationableAdapter {
        viewModel.loadMore(main_search_et.text.toString())
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

        // TODO: можно добавить крестик для стирания текста
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

    private fun openTermActivity(term: Term) {
        val intent = Intent(requireContext(), TermActivity::class.java)
        intent.putExtra(TERM_KEY, term)
        startActivity(intent)
    }
}