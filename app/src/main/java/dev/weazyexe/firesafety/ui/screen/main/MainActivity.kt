package dev.weazyexe.firesafety.ui.screen.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.weazyexe.firesafety.R
import dev.weazyexe.firesafety.ui.screen.main.terms.TermsFragment
import dev.weazyexe.firesafety.ui.screen.main.favorite.FavoriteFragment
import dev.weazyexe.firesafety.ui.screen.main.library.LibraryFragment
import dev.weazyexe.firesafety.ui.screen.main.settings.SettingsFragment
import dev.weazyexe.firesafety.ui.base.InsetsHelper
import dev.weazyexe.firesafety.utils.extensions.useViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Главный экран приложения
 */
class MainActivity : AppCompatActivity() {

    private lateinit var contentFragment: TermsFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var libraryFragment: LibraryFragment
    private lateinit var settingsFragment: SettingsFragment

    private lateinit var active: Fragment

    private var newPosition = 0
    private var startingPosition = 0

    /**
     * Обработчик нажатий на табы [BottomNavigationView]
     */
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
        loadFragments()
        bind()
    }

    /**
     * Настройка инсетов для новых версий Android шобы по красоте
     */
    private fun setupInsets() {
        if (Build.VERSION.SDK_INT >= 27) {
            main_root_view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            InsetsHelper.handleBottom(true, main_bottom_navigation)
        }
    }

    /**
     * Инициализация фрагментов
     */
    private fun loadFragments() {
        contentFragment = TermsFragment()
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

    /**
     * Биндинг всех событий view
     */
    private fun bind() {
        main_bottom_navigation.setOnNavigationItemSelectedListener(
            onNavigationItemReselectedListener
        )
    }

    /**
     * Изменить отображаемый фрагмент на другой
     * @param fragment фрагмент, который отобразится
     */
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().show(fragment).hide(active).commit()
        startingPosition = newPosition
        active.onPause()
        active = fragment
        active.onResume()
    }
}