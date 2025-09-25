import org.modeart.tailor.navigation.OnBoardingNavigation

data class WelcomeScreenUiState(
    val currentPage: Int = 0,
    var initialRoute: String = OnBoardingNavigation.welcome.fullPath
)