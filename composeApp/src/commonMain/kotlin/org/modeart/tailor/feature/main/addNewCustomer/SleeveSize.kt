package org.modeart.tailor.feature.main.addNewCustomer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.armhole
import modearttailor.composeapp.generated.resources.bicep_circumference
import modearttailor.composeapp.generated.resources.forearm_circumference
import modearttailor.composeapp.generated.resources.full_sleeve_length
import modearttailor.composeapp.generated.resources.ic_sleeve
import modearttailor.composeapp.generated.resources.sleeve
import modearttailor.composeapp.generated.resources.sleeve_length_to_elbow
import modearttailor.composeapp.generated.resources.wrist_circumference
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.appTypography

@Composable
fun SleeveSizes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {

        HeaderSection()

        Spacer(modifier = Modifier.height(16.dp))

        val fields = listOf(
            stringResource(Res.string.full_sleeve_length),
            stringResource(Res.string.forearm_circumference),
            stringResource(Res.string.bicep_circumference),
            stringResource(Res.string.wrist_circumference),
            stringResource(Res.string.armhole),
            stringResource(Res.string.sleeve_length_to_elbow)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(64.dp)
                    .background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_sleeve),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(Res.string.sleeve),
                style = appTypography().headline20,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val values = remember { mutableStateMapOf<String, String>() }
        Row {

            VerticalDivider(
                modifier = Modifier.fillMaxHeight().padding(start = 28.dp).offset(y = (-20).dp),
                color = Color(0xFFF2F2F2),
                thickness = 4.dp
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(start = 38.dp, end = 12.dp)
            ) {
                items(fields) { label ->
                    OutlinedTextField(
                        value = values[label] ?: "",
                        onValueChange = { values[label] = it },
                        label = {
                            Text(
                                label,
                                textAlign = TextAlign.End,
                                style = appTypography().body13,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF2F2F2),
                            unfocusedContainerColor = Color(0xFFF2F2F2),
                            focusedBorderColor = Color(0xFFF2F2F2),
                            unfocusedBorderColor = Color(0xFFF2F2F2)
                        )
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun SleeveSizesPreview() {
    SleeveSizes()
}