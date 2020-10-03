package dev.weazyexe.firesafety.ui.screen.main.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.utils.extensions.useViewModel

class LibraryFragment : Fragment() {

    private lateinit var viewModel: LibraryViewModel

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

        viewModel.state.observe(this) {
            if (it == true) {
                Toast.makeText(requireContext(), "write successful", Toast.LENGTH_SHORT).show()
            } else if (it == false) {
                Toast.makeText(requireContext(), "bruh", Toast.LENGTH_SHORT).show()
            }
        }
    }
}