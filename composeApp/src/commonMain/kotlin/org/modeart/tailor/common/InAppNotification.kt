package org.modeart.tailor.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.network_error
import org.jetbrains.compose.resources.stringResource
import org.modeart.tailor.theme.appTypography

const val NOTIFICATION_DELAY = 3000L

@Composable
fun InAppNotification(
    message: String = "",
    isError: Boolean = true,
    isNetworkError: Boolean = false,
    networkErrorCode: String = "",
    onDismiss: () -> Unit = {}
) {
    var isNotificationVisible by remember { mutableStateOf(true) }

    val textMessage = if (isNetworkError)
        stringResource(Res.string.network_error)
    else
        message

    val notificationBackgroundColor = if (isError || networkErrorCode.isNotEmpty())
        Color.Red
    else
        Color.Green

    if (isNotificationVisible) {
        Box(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(8.dp)
                .zIndex(2f)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(notificationBackgroundColor),
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "$networkErrorCode $textMessage",
                color = Color.White,
                maxLines = 1,
                style = appTypography().body13
            )
        }

        // Automatically dismiss notification after a delay
        LaunchedEffect(isNotificationVisible) {
            delay(NOTIFICATION_DELAY)
            isNotificationVisible = false
            onDismiss()
        }
    }
}