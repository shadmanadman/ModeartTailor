package org.modeart.tailor.platform

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                onResult.invoke(SharedImage(BitmapUtils.getBitmapFromUri(uri, contentResolver)))
            }
            onResult.invoke(null)
        }
    return remember {
        GalleryManager(onLaunch = {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        })
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

actual class SharedImage(private val bitmap: android.graphics.Bitmap?) {
    actual fun toByteArray(): ByteArray? {
        return bitmap?.let {
            val byteArrayOutputStream = ByteArrayOutputStream()
            it.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        }
    }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()
        return byteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap()
        }
    }
}

object BitmapUtils {
    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): android.graphics.Bitmap? {
        return try {
            contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}