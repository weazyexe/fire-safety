package dev.weazyexe.firesafety.ui.base

import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.weazyexe.firesafety.utils.extensions.updateMargin

/**
 * Объект, обрабатывающий инсеты сверху и снизу
 */
object InsetsHelper {

    /**
     * Обработка нижних инсетов
     *
     * @param isPadding обработать отступ через padding (true) или margin (false)
     * @param views список [View], для которых применить отступы
     */
    fun handleBottom(isPadding: Boolean, vararg views: View) {
        views.forEach { view ->
            val distance = if (isPadding) view.paddingBottom else view.marginBottom
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                if ((v is FloatingActionButton) && Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT) {
                    val tappable = insets.tappableElementInsets
                    v.updateMargin(
                        bottom = tappable.bottom + v.marginBottom
                    )
                } else {
                    if (isPadding) {
                        v.updatePadding(bottom = distance + insets.systemWindowInsetBottom)
                    } else {
                        v.updateMargin(bottom = distance + insets.systemWindowInsetBottom)
                    }
                }

                insets
            }
        }
    }

    /**
     * Обработка верхних инсетов
     *
     * @param isPadding обработать отступ через padding (true) или margin (false)
     * @param views список [View], для которых применить отступы
     */
    fun handleTop(isPadding: Boolean, vararg views: View) {
        views.forEach { view ->
            // FIXME: временный фикс проблемы с инсетами, потом вернуть distance в updatePadding и updateMargin
            //val distance = if (isPadding) view.paddingTop else view.marginTop
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                if (isPadding) {
                    v.updatePadding(
                        top = insets.systemWindowInsetTop
                    )
                } else {
                    v.updateMargin(
                        top = insets.systemWindowInsetTop
                    )
                }

                insets
            }
        }
    }
}