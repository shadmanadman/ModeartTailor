package org.modeart.tailor.feature.main.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.feature.main.addNewCustomer.customSize.HeaderSection
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun CustomerSizeScene(onBack:()-> Unit){

}

@Preview
@Composable
fun CustomerSizeContent(customer: CustomerProfile,selectedSize: CustomerProfile.Size){
    Column(
        modifier = Modifier.padding(bottom = 72.dp).fillMaxSize().background(Background)
            .padding(horizontal = 16.dp)
    ) {
        HeaderSection(
            age = customer.age ?: "0",
            name = customer.name ?: "",
            phoneNumber = customer.phoneNumber ?: "",
            avatar = customer.avatar ?: ""
        )
    }
}

@Composable
fun SizeTable(
    items: List<Pair<String, String>>
) {
    val borderColor = Primary
    val headerColor = AccentLight

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .background(headerColor)
                .fillMaxWidth()
                .height(40.dp)
        ) {
            TableCell("اندازه", weight = 1f, isHeader = true)
            TableCell("لیست", weight = 2f, isHeader = true)
        }

        // Content Rows
        items.forEach { (value, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                TableCell(value, weight = 1f)
                TableCell(label, weight = 2f)
            }
        }
    }
}


@Composable
fun TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false
) {
    val borderColor = Primary

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .border(0.5.dp, borderColor)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = appTypography().body14,
            fontSize = if (isHeader) 15.sp else 14.sp,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Start
        )
    }
}