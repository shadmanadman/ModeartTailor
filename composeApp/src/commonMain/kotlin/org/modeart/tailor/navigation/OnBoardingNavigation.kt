package org.modeart.tailor.navigation

/**
 * Different scenes in the OnBoarding flow.
 *
 * @constructor Create [OnBoardingNavigation]
 */
object OnBoardingNavigation : Navigation {
    val welcome = Route(name = "welcome")
    val login = Route(name = "login", fullPath = "login/{mode}")
    val signup = Route(name = "signup", fullPath = "signup/{mode}")
    val splash = Route(name = "splash")
    val forgotPassword = Route(
        name = "forgot_password",
        fullPath = "forgot_password/{${ParamKey.IS_FORGOT_PASS}}/{${ParamKey.POPUP_PATH}}"
    )

    object ParamKey {
        const val IS_FORGOT_PASS = "isForgotPass"
        const val POPUP_PATH = "popupPath"
    }

}

