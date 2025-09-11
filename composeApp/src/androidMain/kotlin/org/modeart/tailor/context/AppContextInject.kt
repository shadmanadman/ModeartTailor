package org.modeart.tailor.context

import android.content.Context
import androidx.startup.Initializer
import org.adman.kmp.webview.AppContext

internal class AppContextInject : Initializer<AppContext> {
    override fun create(context: Context): AppContext {
        AppContext.setUp(context)
        return AppContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}