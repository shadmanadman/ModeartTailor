package org.modeart.tailor.feature.main.addNewCustomer.info

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.age
import modearttailor.composeapp.generated.resources.birth_date
import modearttailor.composeapp.generated.resources.choose_gender
import modearttailor.composeapp.generated.resources.customer_basic_info
import modearttailor.composeapp.generated.resources.customer_name_family_name
import modearttailor.composeapp.generated.resources.customer_picture
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_man
import modearttailor.composeapp.generated.resources.ic_upload
import modearttailor.composeapp.generated.resources.ic_woman
import modearttailor.composeapp.generated.resources.mobile_number_customer
import modearttailor.composeapp.generated.resources.save_and_next
import modearttailor.composeapp.generated.resources.save_customer
import modearttailor.composeapp.generated.resources.vector_register_new_customer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerGender
import org.modeart.tailor.platform.PermissionCallback
import org.modeart.tailor.platform.PermissionStatus
import org.modeart.tailor.platform.PermissionType
import org.modeart.tailor.platform.createPermissionsManager
import org.modeart.tailor.platform.rememberCameraManager
import org.modeart.tailor.platform.rememberGalleryManager
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@Composable
@Preview
fun BasicInfo(state: NewCustomerUiState, viewModel: NewCustomerViewModel) {
    var selectedGender by remember { mutableStateOf(state.customer.gender?: CustomerGender.MALE) }
    var customerName by remember { mutableStateOf(state.customer.name) }
    var customerPhoneNumber by remember { mutableStateOf(state.customer.phoneNumber) }
    var customerBirthday by remember { mutableStateOf(state.customer.birthday) }
    var customerAge by remember { mutableStateOf(state.customer.age) }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedBirthdayInDate by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var launchGallery by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    val selectedImageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val selectedImageByteArray = remember { mutableStateOf<ByteArray?>(null) }

    LaunchedEffect(selectedImageByteArray.value) {
        selectedImageByteArray.value?.let {
            viewModel.uploadImage(isAvatar = true,it)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.vector_register_new_customer),
            contentDescription = null
        )
        Text(
            text = stringResource(Res.string.customer_basic_info),
            style = appTypography().headline20
        )

        // Select Gender
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val tabWidth = 70.dp
            val tabHeight = 64.dp
            val animatedOffset by animateDpAsState(
                targetValue = when (selectedGender) {
                    CustomerGender.MALE -> 0.dp
                    CustomerGender.FEMALE -> tabWidth
                    else -> 0.dp
                },
                animationSpec = tween(durationMillis = 500)
            )
            Text(text = stringResource(Res.string.choose_gender), style = appTypography().body13)
            Box(
                modifier = Modifier.width(140.dp).height(tabHeight)
                    .background(color = AccentLight, shape = RoundedCornerShape(12.dp))
                    .clickable(interactionSource = null, indication = null, onClick = {
                        selectedGender = if (selectedGender === CustomerGender.MALE) {
                            CustomerGender.FEMALE
                        } else {
                            CustomerGender.MALE
                        }
                    })
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(tabWidth)
                        .height(tabHeight)
                        .background(color = Color.Black, shape = RoundedCornerShape(12.dp))
                )
                Row(
                    modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(painter = painterResource(Res.drawable.ic_man), contentDescription = null)
                    Image(
                        painter = painterResource(Res.drawable.ic_woman),
                        contentDescription = null
                    )
                }
            }
        }

        // Name, mobile and birthday
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextFieldModeArt(
                value = customerName.toString(),
                onValueChange = {
                    customerName = it
                },
                hint = stringResource(Res.string.customer_name_family_name)
            )
            OutlinedTextFieldModeArt(
                value = customerPhoneNumber.toString(),
                onValueChange = { customerPhoneNumber = it },
                hint = stringResource(Res.string.mobile_number_customer)
            )

            OutlinedTextFieldModeArt(
                value = customerAge?:"",
                isNumberOnly = true,
                onValueChange = {customerAge = it},
                hint = stringResource(Res.string.age)
            )
        }


        // Customer picture
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.customer_picture),
                style = appTypography().body13
            )
            // Selected image
            Box(
                modifier = Modifier.padding(end = 8.dp).size(64.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .clickable(onClick = { launchGallery = true }),
                contentAlignment = Alignment.Center
            ) {
                selectedImageBitmap.value?.let {
                    Image(bitmap = it, contentDescription = null, contentScale = ContentScale.Crop)
                }
            }
            // Upload
            Box(
                modifier = Modifier.padding(end = 16.dp).size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(16.dp))
                    .clickable(onClick = { launchGallery = true }),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(Res.drawable.ic_upload), contentDescription = null)
            }
            // Take picture
            Box(
                modifier = Modifier.size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(16.dp))
                    .clickable(onClick = { launchCamera = true }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add_photo),
                    contentDescription = null
                )
            }
        }

        Row {
            RoundedCornerButton(
                modifier = Modifier.padding(end = 16.dp),
                width = 250,
                isEnabled = true,
                text = stringResource(Res.string.save_and_next),
                onClick = {
                    viewModel.basicInfoChanged(
                        gender = selectedGender ?: CustomerGender.MALE,
                        fullName = customerName ?: "",
                        phoneNumber = customerPhoneNumber ?: "",
                        birth = customerAge ?: "",
                    )
                })

            RoundedCornerButton(
                width = 132,
                isEnabled = true,
                backgroundColor = Accent,
                textColor = Color.Black,
                text = stringResource(Res.string.save_customer),
                onClick = {
                    viewModel.basicInfoChanged(
                        gender = selectedGender ?: CustomerGender.MALE,
                        fullName = customerName ?: "",
                        phoneNumber = customerPhoneNumber ?: "",
                        birth = customerAge ?: "",
                    )
                    viewModel.saveCustomer()
                })

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
                selectedImageByteArray.value = it?.toByteArray()
            }
            withContext(Dispatchers.Default) {
                selectedImageBitmap.value = it?.toImageBitmap()
            }
        }
        launchGallery = false
    }

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                selectedImageByteArray.value = it?.toByteArray()
            }
            withContext(Dispatchers.Default) {
                selectedImageBitmap.value = it?.toImageBitmap()
            }
        }
        launchCamera = false
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

    if (showDatePicker)
        BirthDayPicker(onDateSelected = { millis ->
            customerBirthday = millis.toString()
        }, onDismiss = {
            showDatePicker = false
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDayPicker(onDateSelected: (Long) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    val dateFormatter = remember { DatePickerDefaults.dateFormatter() }
    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
            onDateSelected(datePickerState.selectedDateMillis ?: 0)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onDateSelected(datePickerState.selectedDateMillis ?: 0)
                }
            ) {
                Text("OK")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            dateFormatter = dateFormatter,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(
                    scaleX = 1f,
                    scaleY = 1f,
                    transformOrigin = TransformOrigin(0f, 0f)
                )
        )
    }
}

@OptIn(ExperimentalTime::class)
fun convertMillisToDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val dateTimeInLocalZone = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTimeInLocalZone.month.name} ${dateTimeInLocalZone.day}, ${dateTimeInLocalZone.year}"
}