package org.modeart.tailor.feature.main.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.size.Size
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.filter_sizes
import modearttailor.composeapp.generated.resources.size
import modearttailor.composeapp.generated.resources.title
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.feature.main.addNewCustomer.customSize.HeaderSection
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.model.customer.SizeType
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

data class SizeTable(val name: String, val value: String?)

@Composable
fun CustomerSizeScene(onBack: () -> Unit) {
    val viewModel = koinViewModel(CustomersViewModel::class)
    val state by viewModel.uiState.collectAsState()

    CustomerSizeContent(state.selectedCustomer, state.selectedSize)
}

@Preview
@Composable
fun CustomerSizeContent(customer: CustomerProfile?, selectedSize: CustomerProfile.Size?) {
    val sizeTable = mutableListOf<SizeTable>()

    if (selectedSize!!.type.contains(SizeType.Sleeves))
        sizeTable.addAll(
            mapSleeveSizeToSizeTable(
                selectedSize.sleevesSizes ?: CustomerProfile.SleevesSizes()
            )
        )
    if (selectedSize.type.contains(SizeType.LowerBody))
        sizeTable.addAll(
            mapLowerBodySizeToSizeTable(
                selectedSize.lowerBodySizes ?: CustomerProfile.LowerBodySizes()
            )
        )
    if (selectedSize.type.contains(SizeType.UpperBody))
        sizeTable.addAll(
            mapUpperBodySizeToSizeTable(
                selectedSize.upperBodySizes ?: CustomerProfile.UpperBodySizes()
            )
        )

    var sizeTableState by remember { mutableStateOf(sizeTable) }
    var filterSize by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(filterSize) {
        sizeTableState = sizeTable.filter { it.name.contains(filterSize, ignoreCase = true) }.toMutableList()
    }
    Column(
        modifier = Modifier.padding(top = 34.dp).fillMaxSize().background(Background)
            .padding(horizontal = 16.dp)
            .scrollable(scrollState, orientation = Orientation.Horizontal)
    ) {
        HeaderSection(
            age = customer?.age ?: "0",
            name = customer?.name ?: "",
            phoneNumber = customer?.phoneNumber ?: "",
            avatar = customer?.avatar ?: ""
        )
        OutlinedTextFieldModeArt(
            modifier = Modifier.padding(start = 18.dp),
            hint = stringResource(Res.string.filter_sizes),
            width = 150.dp,
            onValueChange = { filterSize = it },
            value = filterSize
        )
        SizeTable(sizeTableState)
    }
}


@Composable
fun SizeTable(
    items: List<SizeTable>
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
            TableCell(Modifier.weight(1f), stringResource(Res.string.title), isHeader = true)
            TableCell(Modifier.weight(1f), stringResource(Res.string.size), isHeader = true)
        }

        // Content Rows
        items.forEach { (name, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                TableCell(Modifier.weight(1f), name)
                TableCell(Modifier.weight(1f), label ?: "")
            }
        }
    }
}


@Composable
fun TableCell(
    modifier: Modifier,
    text: String,
    isHeader: Boolean = false
) {
    val borderColor = Primary

    Box(
        modifier = modifier
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