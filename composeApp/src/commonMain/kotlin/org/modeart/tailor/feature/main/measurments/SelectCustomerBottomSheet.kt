package org.modeart.tailor.feature.main.measurments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.if_no_customer_selected_then_new_one_will_get_create
import modearttailor.composeapp.generated.resources.search_in_customers
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.stringResource
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerSteps
import org.modeart.tailor.feature.main.customer.CustomersViewModel
import org.modeart.tailor.feature.main.home.CustomerItem
import org.modeart.tailor.theme.appTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCustomerBottomSheet(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val newCustomerViewModel = koinViewModel(NewCustomerViewModel::class)
    val viewModel = koinViewModel(CustomersViewModel::class)
    val state by viewModel.uiState.collectAsState()
    var filterCustomer by remember { mutableStateOf("") }
    var businessCustomers  by remember { mutableStateOf(state.businessCustomers) }

    viewModel.getBusinessCustomers()

    LaunchedEffect(filterCustomer){
        if (filterCustomer.isNotEmpty())
            businessCustomers = state.businessCustomers.filter { it.name!!.contains(filterCustomer, ignoreCase = true) }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(Res.string.if_no_customer_selected_then_new_one_will_get_create),
                    style = appTypography().body14
                )
                OutlinedTextFieldModeArt(
                    hint = stringResource(Res.string.search_in_customers),
                    onValueChange = { filterCustomer = it },
                    value = filterCustomer
                )
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    items(businessCustomers) { customer ->
                        CustomerItem(customerProfile = customer, onCustomerClicked = {
                            newCustomerViewModel.setSelectedCustomer(customer)
                            onDismiss()
                        })
                    }
                }
            }
        })
}