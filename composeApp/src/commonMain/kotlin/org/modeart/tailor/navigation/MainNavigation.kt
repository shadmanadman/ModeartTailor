package org.modeart.tailor.navigation

/**
 * Different scenes in the OnBoarding flow.
 *
 * @constructor Create [MainNavigation]
 */
object MainNavigation : Navigation {
    val main = Route(name = "main")

    val home = Route(name = "home")
    val measure = Route(name = "measure", fullPath = "measure/{mode}")
    val customers = Route(name = "customer", fullPath = "customer/{mode}")
    val note = Route(name = "note")
}

