package org.modeart.tailor.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    isNumberOnly: Boolean = false,
    isEnabled: Boolean = true,
    leadingIcon: DrawableResource? = null,
    onValueChange: (String) -> Unit,
    onSearchCompleted: (String) -> Unit = {},
    onDone: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    TextField(
        modifier = modifier.size(width, height)
            .border(1.dp, borderColor, RoundedCornerShape(roundedCorner))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick()
                    }
                )
            },
        value = value,
        readOnly = isEnabled.not(),
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
        singleLine = isSearch,
        shape = RoundedCornerShape(roundedCorner),
        keyboardOptions = when {
            isSearch -> KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            )

            isNumberOnly -> KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            else -> KeyboardOptions.Default
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchCompleted(value)
            },
            onGo = {
                onDone()
            },
            onDone = {
                onDone()
            },
            onNext = {
                onDone()
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