package org.modeart.tailor.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.AdobeArabicSHINTypoBold
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.regular
import org.jetbrains.compose.resources.Font

data class MyApplicationTypography(
    val body12: TextStyle,
    val body13: TextStyle,
    val body14: TextStyle,
    val title15: TextStyle,
    val title16: TextStyle,
    val title17: TextStyle,
    val title18: TextStyle,
    val headline19: TextStyle,
    val headline20: TextStyle,
    val headline21: TextStyle,
    val headline22: TextStyle,
    val headline23: TextStyle,
    val headline26: TextStyle

)

@Composable
fun getArabicShinFont(): FontFamily {
    return Font(Res.font.AdobeArabicSHINTypoBold).toFontFamily()
}
@Composable
fun appTypography(): MyApplicationTypography {
    val appFontRegular = Font(Res.font.regular).toFontFamily()

    return MyApplicationTypography(
        body12 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        body13 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp
        ),
        body14 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        title15 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        ),
        title16 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        title17 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp
        ),
        title18 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ),
        headline19 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 19.sp
        ),
        headline20 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        headline21 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 21.sp
        ),
        headline22 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp
        ),
        headline23 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 23.sp
        ),
        headline26 = TextStyle(
            fontFamily = appFontRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 26.sp
        )
    )
}