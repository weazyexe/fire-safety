package dev.weazyexe.firesafety.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.utils.extensions.useViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = useViewModel(this, MainViewModel::class.java)
        bind()
    }

    private fun bind() {

    }
}