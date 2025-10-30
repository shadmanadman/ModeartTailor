package org.modeart.tailor.feature.main.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Primary

@Composable
fun CustomerProfileScene() {

}

@Composable
fun CustomerProfileContent() {

}


@Composable
fun ProfileHeaderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Primary),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "پروفایل مریم احمدی",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp) // Space for the icon
                )
                // Arrow Icon
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Go to profile",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Profile Picture
            AsyncImage(
                model = "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).clip(
                    CircleShape
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Number
            Text(
                text = "۰۹۱۷۱۷۴۹۱۱۷", // Phone number
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileActionButton(
                    label = "مدت آشنایی",
                    value = "3 Years"
                ) // Acquaintance Duration
                ProfileActionButton(label = "اندازه گیری", value = "437") // Measurement
                ProfileActionButton(label = "کد", value = "45") // Code
            }
        }
    }
}


@Composable
fun ProfileActionButton(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(100.dp) // Fixed width for buttons
            .height(70.dp)
            .background(AccentLight),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                color = Primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                color = Primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MeasurementItem(date: String, type: String, tag: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(AccentLight)
            .clickable { /* Handle item click */ },
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Aligns items to ends
        ) {
            Card(
                modifier = Modifier.background(AccentLight),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = tag,
                    color = Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            // Right side (Date and Type) - This needs to be a Column and align to end for RTL
            Column(
                horizontalAlignment = Alignment.End // Align text to the right for RTL
            ) {
                Text(
                    text = date,
                    color = Primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = type,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}