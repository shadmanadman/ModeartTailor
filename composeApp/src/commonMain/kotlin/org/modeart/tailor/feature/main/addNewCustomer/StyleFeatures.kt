package org.modeart.tailor.feature.main.addNewCustomer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.body_type
import modearttailor.composeapp.generated.resources.classic
import modearttailor.composeapp.generated.resources.customer_style
import modearttailor.composeapp.generated.resources.fabric_sensitivity
import modearttailor.composeapp.generated.resources.fit
import modearttailor.composeapp.generated.resources.formal
import modearttailor.composeapp.generated.resources.ic_body_type_1
import modearttailor.composeapp.generated.resources.ic_body_type_2
import modearttailor.composeapp.generated.resources.ic_body_type_3
import modearttailor.composeapp.generated.resources.ic_body_type_4
import modearttailor.composeapp.generated.resources.ic_sholder_type_1
import modearttailor.composeapp.generated.resources.ic_sholder_type_2
import modearttailor.composeapp.generated.resources.ic_sholder_type_3
import modearttailor.composeapp.generated.resources.modern
import modearttailor.composeapp.generated.resources.relaxed
import modearttailor.composeapp.generated.resources.select_fabric
import modearttailor.composeapp.generated.resources.shoulder_model
import modearttailor.composeapp.generated.resources.title_personal_and_style_features
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.appTypography

object StyleRes {
    val style_classic = Res.string.classic
    val style_relaxed = Res.string.relaxed
    val style_modern = Res.string.modern
    val style_fit = Res.string.fit
    val style_formal = Res.string.formal
    val body_type_1 = Res.drawable.ic_body_type_1
    val body_type_2 = Res.drawable.ic_body_type_2
    val body_type_3 = Res.drawable.ic_body_type_3
    val body_type_4 = Res.drawable.ic_body_type_4
    val shoulder_1 = Res.drawable.ic_sholder_type_1
    val shoulder_2 = Res.drawable.ic_sholder_type_2
    val shoulder_3 = Res.drawable.ic_sholder_type_3
}

data class SelectionItem(
    val id: Int,
    val name: StringResource = Res.string.classic,
    val imageResId: DrawableResource? = null
)

@Composable
fun PersonalizationScreen() {
    val customerStyles = listOf(
        SelectionItem(
            id = 1,
            name = StyleRes.style_classic
        ),
        SelectionItem(id = 2, name = StyleRes.style_relaxed),
        SelectionItem(id = 3, name = StyleRes.style_modern),
        SelectionItem(id = 4, name = StyleRes.style_fit),
        SelectionItem(id = 5, name = StyleRes.style_formal)
    )

    val bodyTypes = listOf(
        SelectionItem(id = 1, imageResId = StyleRes.body_type_1),
        SelectionItem(id = 2, imageResId = StyleRes.body_type_2),
        SelectionItem(id = 3, imageResId = StyleRes.body_type_3),
        SelectionItem(id = 4, imageResId = StyleRes.body_type_4)
    )

    val shoulderModels = listOf(
        SelectionItem(id = 1, imageResId = StyleRes.shoulder_1),
        SelectionItem(id = 2, imageResId = StyleRes.shoulder_2),
        SelectionItem(id = 3, imageResId = StyleRes.shoulder_3)
    )

    var selectedStyle by remember { mutableStateOf<SelectionItem?>(customerStyles.first()) }
    var selectedBodyType by remember { mutableStateOf<SelectionItem?>(bodyTypes.first()) }
    var selectedShoulder by remember { mutableStateOf<SelectionItem?>(shoulderModels.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {
        HeaderSection(title = stringResource(Res.string.title_personal_and_style_features))

        Spacer(modifier = Modifier.height(24.dp))

        // Customer Style Section
        SelectionSection(
            title = stringResource(Res.string.customer_style),
            items = customerStyles,
            selectedItem = selectedStyle,
            onItemSelected = { selectedStyle = it },
            isTextOnly = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Body Type Section
        SelectionSection(
            title = stringResource(Res.string.body_type),
            items = bodyTypes,
            selectedItem = selectedBodyType,
            onItemSelected = { selectedBodyType = it },
            isImageOnly = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Shoulder Model Section
        SelectionSection(
            title = stringResource(Res.string.shoulder_model),
            items = shoulderModels,
            selectedItem = selectedShoulder,
            onItemSelected = { selectedShoulder = it },
            isImageOnly = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Fabric Sensitivity Section
        FabricSensitivitySection()
    }
}

@Composable
fun HeaderSection(title: String) {
    Text(
        text = title,
        style = appTypography().headline20,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SelectionSection(
    title: String,
    items: List<SelectionItem>,
    selectedItem: SelectionItem?,
    onItemSelected: (SelectionItem) -> Unit,
    isTextOnly: Boolean = false,
    isImageOnly: Boolean = false
) {
    Text(
        text = title,
        style = appTypography().title18,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            SelectionBox(
                item = item,
                isSelected = item == selectedItem,
                onClick = { onItemSelected(item) },
                isTextOnly = isTextOnly,
                isImageOnly = isImageOnly
            )
        }
    }
}

@Composable
fun SelectionBox(
    item: SelectionItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    isTextOnly: Boolean,
    isImageOnly: Boolean
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )
    val shadowElevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .then(
                when {
                    isTextOnly -> Modifier.size(120.dp, 60.dp)
                    isImageOnly -> Modifier.size(80.dp, 120.dp)
                    else -> Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isTextOnly) {
            Text(
                text = stringResource(item.name),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        } else if (isImageOnly && item.imageResId != null) {
            Image(
                painter = painterResource(item.imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }
    }
}

@Composable
fun FabricSensitivitySection() {
    Text(
        text = stringResource(Res.string.fabric_sensitivity),
        style = appTypography().title18,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = stringResource(Res.string.select_fabric),
            color = Color.Gray,
            style = appTypography().title16,
        )
    }
}

@Preview()
@Composable
fun PersonalizationScreenPreview() {
    PersonalizationScreen()
}