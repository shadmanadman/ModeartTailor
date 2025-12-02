package org.modeart.tailor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        if (Intent.ACTION_VIEW == action && data != null) {
            if (data.scheme == "modeart" && data.host == "payment_result") {

                val authority = data.getQueryParameter("authority")
                val status = data.getQueryParameter("status")

                println("Payment Status: $status")
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}