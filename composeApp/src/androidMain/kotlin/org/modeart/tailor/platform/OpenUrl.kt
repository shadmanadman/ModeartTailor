package org.modeart.tailor.platform

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import org.adman.kmp.webview.AppContext

actual fun openWebBrowser(url: String) {
    val intent = CustomTabsIntent.Builder().build().apply {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    return try {
        val context = AppContext.get()
        intent.launchUrl(context, url.toUri())
    } catch (e: Exception) {
        println("${e.message}")
    }
}