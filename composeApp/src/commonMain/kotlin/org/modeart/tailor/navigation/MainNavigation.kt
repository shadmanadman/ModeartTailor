package org.modeart.tailor.navigation

/**
 * Different scenes in the OnBoarding flow.
 *
 * @constructor Create [MainNavigation]
 */
object MainNavigation : Navigation {
    val main = Route(name = "main")

    val home = Route(name = "home")
    //profile
    val profile = Route(name = "profile")
    val editProfile = Route(name = "editProfile")
    val contact = Route(name = "contact")
    val about = Route(name = "about")


    // Note
    val newNote = Route(name = "newNote")
    val notes = Route(name = "notes")

    val measure = Route(name = "measure", fullPath = "measure/{mode}")
    val customers = Route(name = "customer", fullPath = "customer/{mode}")
    val note = Route(name = "note")
}

