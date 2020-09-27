package dev.weazyexe.firesafety.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.ui.content.ContentFragment
import dev.weazyexe.firesafety.ui.favorite.FavoriteFragment
import dev.weazyexe.firesafety.ui.library.LibraryFragment
import dev.weazyexe.firesafety.ui.settings.SettingsFragment
import dev.weazyexe.firesafety.utils.InsetsHelper
import dev.weazyexe.firesafety.utils.extensions.useViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var contentFragment: ContentFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var libraryFragment: LibraryFragment
    private lateinit var settingsFragment: SettingsFragment

    private lateinit var active: Fragment

    private var newPosition = 0
    private var startingPosition = 0

    private val onNavigationItemReselectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_content -> {
                    newPosition = 0
                    if (startingPosition != newPosition) {
                        changeFragment(contentFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_favorites -> {
                    newPosition = 1
                    if (startingPosition != newPosition) {
                        changeFragment(favoriteFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_library -> {
                    newPosition = 2
                    if (startingPosition != newPosition) {
                        changeFragment(libraryFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_settings -> {
                    newPosition = 3
                    if (startingPosition != newPosition) {
                        changeFragment(settingsFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupInsets()

        viewModel = useViewModel(this, MainViewModel::class.java)
        loadFragments()
        bind()
    }

    private fun setupInsets() {
        if (Build.VERSION.SDK_INT >= 27) {
            main_root_view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            InsetsHelper.handleBottom(true, main_bottom_navigation)
        }
    }

    private fun bind() {
        main_bottom_navigation.setOnNavigationItemSelectedListener(
            onNavigationItemReselectedListener
        )
    }

    private fun loadFragments() {
        contentFragment = ContentFragment()
        favoriteFragment = FavoriteFragment()
        libraryFragment = LibraryFragment()
        settingsFragment = SettingsFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_fragment_layout, contentFragment)

            add(R.id.main_fragment_layout, favoriteFragment)
            hide(favoriteFragment)

            add(R.id.main_fragment_layout, libraryFragment)
            hide(libraryFragment)

            add(R.id.main_fragment_layout, settingsFragment)
            hide(settingsFragment)

            commit()
        }

        main_bottom_navigation.selectedItemId = R.id.main_content
        active = contentFragment
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().show(fragment).hide(active).commit()
        startingPosition = newPosition
        active.onPause()
        active = fragment
        active.onResume()
    }
}