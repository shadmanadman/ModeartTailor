package org.modeart.tailor.feature.main.addNewCustomer.info

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.add_photo
import modearttailor.composeapp.generated.resources.body_dress_pattern_measurement
import modearttailor.composeapp.generated.resources.fit_fit
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_arrow_down
import modearttailor.composeapp.generated.resources.ic_meaure_size
import modearttailor.composeapp.generated.resources.ic_meaure_source
import modearttailor.composeapp.generated.resources.ic_note
import modearttailor.composeapp.generated.resources.ic_upload
import modearttailor.composeapp.generated.resources.important_note
import modearttailor.composeapp.generated.resources.loose_fit
import modearttailor.composeapp.generated.resources.save_and_next
import modearttailor.composeapp.generated.resources.notes_hint
import modearttailor.composeapp.generated.resources.register_title
import modearttailor.composeapp.generated.resources.save_customer
import modearttailor.composeapp.generated.resources.seam_allowance
import modearttailor.composeapp.generated.resources.title_measurement_freedom_level
import modearttailor.composeapp.generated.resources.title_measurement_source
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.feature.main.addNewCustomer.customSize.HeaderSection
import org.modeart.tailor.model.customer.CustomerSizeFreedom
import org.modeart.tailor.model.customer.CustomerSizeSource
import org.modeart.tailor.platform.PermissionCallback
import org.modeart.tailor.platform.PermissionStatus
import org.modeart.tailor.platform.PermissionType
import org.modeart.tailor.platform.createPermissionsManager
import org.modeart.tailor.platform.rememberCameraManager
import org.modeart.tailor.platform.rememberGalleryManager
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Hint
import org.modeart.tailor.theme.appTypography

@Composable
fun FinalInfo(state: NewCustomerUiState, viewModel: NewCustomerViewModel) {
    var selectedFit by remember { mutableStateOf(state.customer.sizeFreedom) }
    var customerSizeSource by remember { mutableStateOf(state.customer.sizeSource) }
    var importantNote by remember { mutableStateOf(state.customer.importantNote) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(
            age = state.customer.age ?: "",
            name = state.customer.name ?: "",
            phoneNumber = state.customer.phoneNumber ?: "",
            avatar = state.customer.avatar ?: ""
        )
        // Measurement Source Section
        FormSectionTitle(
            title = stringResource(Res.string.title_measurement_source),
            Res.drawable.ic_meaure_source
        )
        DropdownField(hint = stringResource(Res.string.body_dress_pattern_measurement))

        Spacer(modifier = Modifier.height(24.dp))

        // Measurement Freedom Level Section
        FormSectionTitle(
            title = stringResource(Res.string.title_measurement_freedom_level),
            Res.drawable.ic_meaure_size
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            SelectableButton(
                text = stringResource(Res.string.seam_allowance),
                isSelected = selectedFit == CustomerSizeFreedom.WithAllowance,
                onClick = { selectedFit = CustomerSizeFreedom.WithAllowance }
            )
            SelectableButton(
                text = stringResource(Res.string.loose_fit),
                isSelected = selectedFit == CustomerSizeFreedom.Free,
                onClick = { selectedFit = CustomerSizeFreedom.Free }
            )
            SelectableButton(
                text = stringResource(Res.string.fit_fit),
                isSelected = selectedFit == CustomerSizeFreedom.Fit,
                onClick = { selectedFit = CustomerSizeFreedom.Fit }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Add Photo Section
        AddPhotoSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Important Note Section
        FormSectionTitle(title = stringResource(Res.string.important_note), Res.drawable.ic_note)
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            value = importantNote ?: "",
            onValueChange = {
                importantNote = it
            },
            hint = stringResource(Res.string.notes_hint)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Submit Button
        RoundedCornerButton(
            width = 332,
            isEnabled = true,
            text = stringResource(Res.string.save_customer),
            onClick = {
                viewModel.finalInfoChanged(
                    sizeSource = customerSizeSource ?: CustomerSizeSource.Body,
                    sizeFreedom = selectedFit ?: CustomerSizeFreedom.WithAllowance,
                    extraPhoto = "",
                    importantNote = importantNote ?: ""
                )
                viewModel.saveCustomer()
            })
    }
}

@Composable
fun FormSectionTitle(title: String, iconRes: DrawableResource) {
    Row(
        Modifier.padding(bottom = 12.dp, top = 32.dp).fillMaxWidth().height(32.dp)
            .background(color = Hint.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
            .padding(start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null
        )

        Text(
            text = title,
            style = appTypography().title15,
            fontWeight = FontWeight.SemiBold,
        )
    }
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
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else AccentLight,
        animationSpec = tween(durationMillis = 500)
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black,
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
        modifier = modifier
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
            color = textColor,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AddPhotoSection() {
    val coroutineScope = rememberCoroutineScope()

    var launchGallery by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    val selectedImageBitmap =  remember { mutableListOf<ImageBitmap>() }
    val selectedImageByteArray = remember { mutableListOf<ByteArray>() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.add_photo),
            style = appTypography().title16,
            modifier = Modifier.weight(1f).padding(end = 16.dp)
        )

        LazyRow {
            items(selectedImageBitmap.size) {
                Box(
                    modifier = Modifier.padding(end = 8.dp).size(64.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { selectedImageBitmap.removeAt(it) }, modifier = Modifier.size(8.dp)) {
                        Image(Icons.Default.Close, contentDescription = null)
                    }
                    Image(
                        selectedImageBitmap[it],
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AddPhotoButton(iconRes = Res.drawable.ic_add_photo) {
                launchCamera = true
            }
            AddPhotoButton(iconRes = Res.drawable.ic_upload) {
                launchGallery = true
            }
        }
    }


    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.GALLERY -> launchGallery = true
                        PermissionType.CAMERA -> launchCamera = true
                    }
                }

                else -> {
                    launchSetting = true
                }
            }
        }
    })


    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                it?.toByteArray()?.let {
                    selectedImageByteArray.add(it)
                }
            }
            withContext(Dispatchers.Default) {
                it?.toImageBitmap()?.let {
                    selectedImageBitmap.add(it)
                }
            }
            launchGallery = false
        }
    }

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                it?.toByteArray()?.let {
                    selectedImageByteArray.add(it)
                }
            }
            withContext(Dispatchers.Default) {
                it?.toImageBitmap()?.let {
                    selectedImageBitmap.add(it)
                }
            }
            launchCamera = false
        }
    }

    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY))
            galleryManager.launch()
        else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
    }

    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA))
            cameraManager.launch()
        else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
    }

    if (launchSetting)
        permissionsManager.launchSettings()
}

@Composable
fun AddPhotoButton(iconRes: DrawableResource, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AccentLight)
            .clickable { onClick() },
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
fun NoteTextField(hint: String, importantNote: String) {
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
    //FinalInfo(NewCustomerUiState())
}