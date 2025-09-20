package org.modeart.tailor.feature.main.addNewCustomer.fastSize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.next
import modearttailor.composeapp.generated.resources.register_new_customer
import modearttailor.composeapp.generated.resources.register_title
import modearttailor.composeapp.generated.resources.select_customer_size
import modearttailor.composeapp.generated.resources.vector_fast_size
import modearttailor.composeapp.generated.resources.vector_register_new_customer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.info.SelectableButton
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography

@Composable
fun SizeSelectionScreen() {

}

@Composable
@Preview
fun SizeSelectionContent() {
    var selectedSize by remember { mutableStateOf(32) }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.vector_fast_size),
            contentDescription = null
        )

        Text(
            text = stringResource(Res.string.select_customer_size),
            textAlign = TextAlign.Center,
            style = appTypography().title15.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = stringResource(Res.string.register_new_customer),
            textAlign = TextAlign.Center,
            style = appTypography().body13
        )


        val sizeList =
            listOf(
                32,
                34,
                36,
                38,
                40,
                42,
                44,
                46,
                48,
                50,
                52,
                54,
                56,
                58,
                60
            )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().weight(1f).padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sizeList.size) {
                SelectableButton(
                    modifier = Modifier.size(50.dp),
                    text = sizeList[it].toString(),
                    isSelected = selectedSize == sizeList[it],
                    onClick = {
                        selectedSize = sizeList[it]
                    })
            }
        }

        RoundedCornerButton(isEnabled = true, text = stringResource(Res.string.next)) {

        }
    }
}