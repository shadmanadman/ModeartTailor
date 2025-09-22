package org.modeart.tailor.feature.main.addNewCustomer.info

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.customer_preferred_color
import modearttailor.composeapp.generated.resources.ic_color
import modearttailor.composeapp.generated.resources.ic_fabric
import modearttailor.composeapp.generated.resources.ic_note
import modearttailor.composeapp.generated.resources.ic_search
import modearttailor.composeapp.generated.resources.ic_user_star
import modearttailor.composeapp.generated.resources.is_old_customer
import modearttailor.composeapp.generated.resources.next
import modearttailor.composeapp.generated.resources.no
import modearttailor.composeapp.generated.resources.notes_about_customer
import modearttailor.composeapp.generated.resources.notes_hint
import modearttailor.composeapp.generated.resources.referred_by
import modearttailor.composeapp.generated.resources.search_in_customers
import modearttailor.composeapp.generated.resources.title_supplementary_information
import modearttailor.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerColor
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Hint
import org.modeart.tailor.theme.appTypography

@Composable
fun SupplementaryInformationScreen(state: NewCustomerUiState, viewModel: NewCustomerViewModel) {
    var selectedColor by remember { mutableStateOf(state.customer.customerColor) }
    var isOldCustomer by remember { mutableStateOf<Boolean?>(null) }
    var customerNotes by remember { mutableStateOf(state.customer.overallNote) }
    var referredBy by remember { mutableStateOf(state.customer.referredBy) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {
        // Title Section
        Text(
            text = stringResource(Res.string.title_supplementary_information),
            style = appTypography().headline20,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Customer Preferred Color Section
        Row(
            Modifier.padding(bottom = 12.dp).fillMaxWidth().height(32.dp)
                .background(color = Hint.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(painter = painterResource(Res.drawable.ic_color), contentDescription = null)

            Text(
                text = stringResource(Res.string.customer_preferred_color),
                style = appTypography().title15,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            val colors = listOf(
                Color(0xFF000000),
                Color(0xFF382CD0),
                Color(0xFFFF4A4A),
                Color(0xFFFBBC05),
                Color(0xFFFFFFFF)
            )
            colors.forEachIndexed { index, color ->
                ColorSelectionBox(
                    color = color,
                    isSelected = selectedColor == index.mapToCustomerColor(),
                    onClick = { selectedColor = index.mapToCustomerColor() }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Is Old Customer Section
        Row(
            Modifier.padding(bottom = 12.dp).fillMaxWidth().height(32.dp)
                .background(color = Hint.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(painter = painterResource(Res.drawable.ic_user_star), contentDescription = null)
            Text(
                text = stringResource(Res.string.is_old_customer),
                style = appTypography().title15,
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            YesNoButton(
                text = stringResource(Res.string.no),
                isSelected = isOldCustomer == false,
                onClick = { isOldCustomer = false }
            )
            YesNoButton(
                text = stringResource(Res.string.yes),
                isSelected = isOldCustomer == true,
                onClick = { isOldCustomer = true }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Referred By Section
        Row(
            Modifier.padding(bottom = 12.dp).fillMaxWidth().height(32.dp)
                .background(color = Hint.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(painter = painterResource(Res.drawable.ic_fabric), contentDescription = null)
            Text(
                text = stringResource(Res.string.referred_by),
                style = appTypography().title15,
                fontWeight = FontWeight.SemiBold
            )
        }
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth(),
            hint = stringResource(Res.string.search_in_customers),
            value = state.customer.referredBy ?: "",
            leadingIcon = Res.drawable.ic_search,
            onValueChange = {
                referredBy = it
            })

        Spacer(modifier = Modifier.height(24.dp))

        // Notes Section
        Row(
            Modifier.padding(bottom = 12.dp).fillMaxWidth().height(32.dp)
                .background(color = Hint.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(painter = painterResource(Res.drawable.ic_note), contentDescription = null)
            Text(
                text = stringResource(Res.string.notes_about_customer),
                style = appTypography().title15,
                fontWeight = FontWeight.SemiBold,
            )
        }
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth().height(180.dp),
            hint = stringResource(Res.string.notes_hint),
            value = customerNotes ?: "",
            onValueChange = {
                customerNotes = it
            })

        RoundedCornerButton(
            width = 332,
            isEnabled = true,
            text = stringResource(Res.string.next),
            onClick = {
                viewModel.supplementaryInfoChanged(
                    customerColor = selectedColor ?: CustomerColor.Black,
                    isOldCustomer = isOldCustomer ?: false,
                    referredBy = referredBy ?: "",
                    overallNote = customerNotes ?: ""
                )
            })
    }
}

@Composable
fun ColorSelectionBox(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.Transparent,
        animationSpec = tween(durationMillis = 500)
    )
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}

@Composable
fun YesNoButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else AccentLight,
        animationSpec = tween(durationMillis = 500)
    )
    val shadowElevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .width(120.dp)
            .height(60.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 16.sp, style = appTypography().title15)
    }
}

@Composable
fun SimpleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isMultiLine: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isMultiLine) 160.dp else 60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.fillMaxSize(),
            singleLine = !isMultiLine
        )
        if (value.isEmpty()) {
            Text(
                text = hint,
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview()
@Composable
fun SupplementaryInformationScreenPreview() {
    //SupplementaryInformationScreen()
}

fun Int.mapToCustomerColor(): CustomerColor {
    return when (this) {
        0 -> CustomerColor.Black
        1 -> CustomerColor.White
        2 -> CustomerColor.Red
        3 -> CustomerColor.Yellow
        4 -> CustomerColor.Purple
        else -> CustomerColor.White
    }
}