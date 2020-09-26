package dev.weazyexe.firesafety.ui.main

import androidx.lifecycle.ViewModel
import dev.weazyexe.firesafety.app.App

class MainViewModel: ViewModel() {

    init {
        App.component.inject(this)
    }
}