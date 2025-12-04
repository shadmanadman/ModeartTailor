package org.modeart.tailor.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openWebBrowser(url: String) {
    val website = NSURL(string = url)
    return if (UIApplication.sharedApplication().canOpenURL(website)) {
        runCatching {
            UIApplication.sharedApplication()
                .openURL(
                    website,
                    mapOf<Any?, Any>()
                ) { success ->
                }
        }.getOrElse {
            println("Failed to open the URL. ${it.message}")
        }
    } else {
        println("Couldn't open the URL.")
    }
}