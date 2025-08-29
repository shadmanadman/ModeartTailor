package org.modeart.tailor.feature.main.addNewCustomer

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.add_photo
import modearttailor.composeapp.generated.resources.body_dress_pattern_measurement
import modearttailor.composeapp.generated.resources.fit_fit
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_arrow_down
import modearttailor.composeapp.generated.resources.ic_upload
import modearttailor.composeapp.generated.resources.important_note
import modearttailor.composeapp.generated.resources.loose_fit
import modearttailor.composeapp.generated.resources.notes_example
import modearttailor.composeapp.generated.resources.register_title
import modearttailor.composeapp.generated.resources.seam_allowance
import modearttailor.composeapp.generated.resources.title_measurement_freedom_level
import modearttailor.composeapp.generated.resources.title_measurement_source
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.appTypography

@Composable
fun MeasurementFormScreen() {
    var selectedFit by remember { mutableStateOf<String?>("فیت") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Measurement Source Section
        FormSectionTitle(title = stringResource(Res.string.title_measurement_source))
        DropdownField(hint = stringResource(Res.string.body_dress_pattern_measurement))

        Spacer(modifier = Modifier.height(24.dp))

        // Measurement Freedom Level Section
        FormSectionTitle(title = stringResource(Res.string.title_measurement_freedom_level))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            SelectableButton(
                text = stringResource(Res.string.seam_allowance),
                isSelected = selectedFit == "با جای دوخت",
                onClick = { selectedFit = "با جای دوخت" }
            )
            SelectableButton(
                text = stringResource(Res.string.loose_fit),
                isSelected = selectedFit == "آزاد",
                onClick = { selectedFit = "آزاد" }
            )
            SelectableButton(
                text = stringResource(Res.string.fit_fit),
                isSelected = selectedFit == "فیت",
                onClick = { selectedFit = "فیت" }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Add Photo Section
        AddPhotoSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Important Note Section
        FormSectionTitle(title = stringResource(Res.string.important_note))
        NoteTextField(hint = stringResource(Res.string.notes_example))

        Spacer(modifier = Modifier.weight(1f))

        // Submit Button
        SubmitButton()
    }
}

@Composable
fun FormSectionTitle(title: String) {
    Text(
        text = title,
        style = appTypography().title16,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun DropdownField(hint: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { /* Handle dropdown click */ }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(text = hint, color = Color.Gray, style = appTypography().title16)
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_down),
            contentDescription = "Dropdown Arrow",
            tint = Color.Gray,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
fun SelectableButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color(0xFFE0E0E0),
        animationSpec = tween(durationMillis = 500)
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.Transparent,
        animationSpec = tween(durationMillis = 500)
    )
    val shadowElevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .height(60.dp)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = appTypography().body14,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AddPhotoSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.add_photo),
            style = appTypography().title16,
            modifier = Modifier.padding(end = 16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AddPhotoButton(iconRes = Res.drawable.ic_add_photo)
            AddPhotoButton(iconRes = Res.drawable.ic_upload)
        }
    }
}

@Composable
fun AddPhotoButton(iconRes: DrawableResource) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { /* Handle photo click */ },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun NoteTextField(hint: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(text = hint, color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun SubmitButton() {
    Button(
        onClick = { /* Handle submit */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
    ) {
        Text(
            text = stringResource(Res.string.register_title),
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun MeasurementFormScreenPreview() {
    MeasurementFormScreen()
}