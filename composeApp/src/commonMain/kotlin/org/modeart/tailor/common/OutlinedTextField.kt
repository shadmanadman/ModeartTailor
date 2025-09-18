package org.modeart.tailor.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.modeart.tailor.theme.Hint
import org.modeart.tailor.theme.appTypography

@Composable
fun OutlinedTextFieldModeArt(
    modifier: Modifier = Modifier,
    width: Dp = 256.dp,
    height: Dp = 54.dp,
    value: String,
    borderColor: Color = Hint,
    roundedCorner: Dp = 18.dp,
    hint: String,
    hintColor: Color = Hint,
    isSearch: Boolean = false,
    leadingIcon: DrawableResource? = null,
    onValueChange: (String) -> Unit,
    onSearchCompleted:(String)-> Unit = {}
) {
    TextField(
        modifier = modifier.size(width, height)
            .border(1.dp, borderColor, RoundedCornerShape(roundedCorner)),
        value = value,
        textStyle = appTypography().body13,
        leadingIcon = if (leadingIcon != null) {
            {
                androidx.compose.material3.Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = null
                )
            }
        } else {
            null
        },
        onValueChange = onValueChange,
        shape = RoundedCornerShape(roundedCorner),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchCompleted(value)
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        label = {
            Text(hint, color = hintColor, style = appTypography().body13)
        }
    )
}