package dev.weazyexe.firesafety.ui.base

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import dev.weazyexe.firesafety.R

/**
 * Помощник для работы с Custom Tabs
 */
object CustomTabsHelper {

    /**
     * Открыть url в Custom Chrome Tabs. Если не получилось - то в любом другом браузере.
     */
    fun openLink(activity: Activity, url: String) {
        val linkUri = buildUri(url)
        val customTabsIntent =
            buildCustomTabsIntent(activity, R.color.mintCreamColor, R.color.mintCreamColor)

        try {
            customTabsIntent.launchUrl(activity, linkUri)
        } catch (e: Throwable) {
            val browserIntent = Intent(ACTION_VIEW, linkUri)
            activity.startActivity(browserIntent)
        }
    }

    private fun buildUri(url: String): Uri {
        var uri = Uri.parse(url)
        if (uri.scheme == null) {
            uri = uri.buildUpon().scheme("http").build()
        }
        return uri
    }

    private fun buildCustomTabsIntent(
        activity: Activity,
        @ColorRes toolbarColorId: Int,
        @ColorRes secondaryToolbarColorId: Int
    ): CustomTabsIntent {
        return CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(activity, toolbarColorId))
            .setSecondaryToolbarColor(ContextCompat.getColor(activity, secondaryToolbarColorId))
            .build()
    }
}
