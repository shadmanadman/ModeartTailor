package org.modeart.tailor.navigation

import androidx.compose.runtime.Composable

/**
 * Base navigation.
 *
 * @constructor Create [Navigation]
 */
interface Navigation {
    val back: Route get() = Route("back")
}

/**
 * Route information.
 *
 * @property name
 * @property fullPath
 * @property scene
 * @constructor Create [Route]
 */
data class Route(
    val name: String,
    val fullPath: String = name,
    val scene: @Composable () -> Unit = {},
) {
    companion object {
        /**
         * Pass data into route.
         *
         * @param T
         * @param data
         */
        fun <T> Route.addToPath(data: T) = copy(fullPath = "$name/$data")
    }
}
