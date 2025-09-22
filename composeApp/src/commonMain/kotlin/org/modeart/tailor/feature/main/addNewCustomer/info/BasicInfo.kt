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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
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
import modearttailor.composeapp.generated.resources.next
import modearttailor.composeapp.generated.resources.vector_register_new_customer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerGender
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography


@Composable
@Preview
fun BasicInfo(state: NewCustomerUiState, viewModel: NewCustomerViewModel) {
    var selectedGender by remember { mutableStateOf(state.customer.gender) }
    var customerName by remember { mutableStateOf(state.customer.name) }
    var customerPhoneNumber by remember { mutableStateOf(state.customer.phoneNumber) }
    var customerBirthday by remember { mutableStateOf(state.customer.birthday) }
    var customerAvatar by remember { mutableStateOf(state.customer.avatar) }

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
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(tabWidth)
                        .height(tabHeight)
                        .clickable(onClick = {
                            selectedGender = if (animatedOffset == 0.dp)
                                CustomerGender.MALE
                            else
                                CustomerGender.FEMALE
                        })
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
                value = state.customer.name.toString(),
                onValueChange = {},
                hint = stringResource(Res.string.customer_name_family_name)
            )
            OutlinedTextFieldModeArt(
                value = state.customer.phoneNumber.toString(),
                onValueChange = {},
                hint = stringResource(Res.string.mobile_number_customer)
            )
            OutlinedTextFieldModeArt(
                value = state.customer.birthday.toString(),
                onValueChange = {},
                hint = stringResource(Res.string.birth_date)
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

            // Upload
            Box(
                modifier = Modifier.padding(end = 16.dp).size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(Res.drawable.ic_upload), contentDescription = null)
            }
            // Take picture
            Box(
                modifier = Modifier.size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add_photo),
                    contentDescription = null
                )
            }
        }

        RoundedCornerButton(
            width = 332,
            isEnabled = true,
            text = stringResource(Res.string.next),
            onClick = {
                viewModel.basicInfoChanged(
                    gender = selectedGender ?: CustomerGender.MALE,
                    fullName = customerName ?: "",
                    phoneNumber = customerPhoneNumber ?: "",
                    birth = customerBirthday ?: "",
                    customerAvatar = customerAvatar ?: ""
                )
            })
    }
}