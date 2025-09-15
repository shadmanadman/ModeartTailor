package org.modeart.tailor.prefs

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toOkioPath
import org.adman.kmp.webview.AppContext
import java.io.File

actual fun rememberDataStore(): PrefsDataStore {
    return PreferenceDataStoreFactory.createWithPath {
        File(AppContext.get().filesDir, dataStoreFileName).toOkioPath()
    }
}