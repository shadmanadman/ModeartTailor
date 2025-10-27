package org.modeart.tailor.feature.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.all_customer
import modearttailor.composeapp.generated.resources.first_customer
import modearttailor.composeapp.generated.resources.first_note
import modearttailor.composeapp.generated.resources.ic_add_button
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_calendar
import modearttailor.composeapp.generated.resources.ic_notification
import modearttailor.composeapp.generated.resources.ic_search
import modearttailor.composeapp.generated.resources.ic_target
import modearttailor.composeapp.generated.resources.ic_user_star
import modearttailor.composeapp.generated.resources.last_customers
import modearttailor.composeapp.generated.resources.last_notes
import modearttailor.composeapp.generated.resources.search_in_customers
import modearttailor.composeapp.generated.resources.see_all
import modearttailor.composeapp.generated.resources.test_avatar
import modearttailor.composeapp.generated.resources.this_month_customer
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.api.BASE_URL
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.feature.main.main.BottomNavViewModel
import org.modeart.tailor.feature.main.main.contract.RootBottomNavId
import org.modeart.tailor.feature.main.profile.ProfileViewModel
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.feature.onboarding.welcome.WELCOME_PAGE_COUNT
import org.modeart.tailor.feature.onboarding.welcome.WelcomeIndicators
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.appTypography

@Composable
fun HomeScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(HomeViewModel::class)
    val bottomNavViewModel = koinViewModel(BottomNavViewModel::class)

    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<HomeUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is HomeUiEffect.Navigation -> onNavigate(effect.screen)
                is HomeUiEffect.ShowRawNotification -> {
                    notification = effect
                }
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }

    Column {
        Statistics(
            allCustomersCount = state.customerCount,
            thisMonthCustomer = state.thisMonthCustomer
        )
        SectionTitle(stringResource(Res.string.last_notes)){
            bottomNavViewModel.openScreen(RootBottomNavId.Note)
        }
        LastNoteItem(state.latestNotes) {
            onNavigate(MainNavigation.newNote)
        }
        SectionTitle(stringResource(Res.string.last_customers)){
            bottomNavViewModel.openScreen(RootBottomNavId.Customer)
        }
        LatestCustomerSection(state.latestCustomers) {
            bottomNavViewModel.openScreen(RootBottomNavId.Measure)
        }
    }
}

@Composable
fun SectionTitle(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(22.dp)
            .clickable(interactionSource = null, indication = null, onClick =  onClick),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = appTypography().title15)
        Text(text = stringResource(Res.string.see_all), style = appTypography().body12)
    }
}


@Composable
fun LastNoteItem(
    lastThreeNotes: List<BusinessProfile.Notes> = emptyList(),
    onNavigateToNewNote: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { lastThreeNotes.size })

    if (lastThreeNotes.isNotEmpty())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalPager(state = pagerState) { page ->
                Box(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
                        .defaultMinSize(minHeight = 136.dp)
                        .background(color = Color.Black, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Title
                            Text(
                                text = lastThreeNotes[page].title,
                                style = appTypography().title17.copy(color = Color.White)
                            )
                            // Date
                            Box(
                                modifier = Modifier.background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(16.dp)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_calendar),
                                        contentDescription = null
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 8.dp),
                                        text = "22 Aug",
                                        style = appTypography().body12
                                    )
                                }
                            }
                        }
                        // Note content
                        Text(
                            text = lastThreeNotes[page].content,
                            style = appTypography().body14.copy(color = Color.White)
                        )
                    }
                }
            }
            WelcomeIndicators(pageSize = pagerState.pageCount,pagerState.currentPage)
        }
    else
        EmptyNoteOrCustomer(isNote = true, onCardClicked = {
            onNavigateToNewNote()
        })
}


@Composable
fun EmptyNoteOrCustomer(isNote: Boolean, onCardClicked: () -> Unit) {
    val emptyTitle = if (isNote)
        stringResource(Res.string.first_note)
    else
        stringResource(Res.string.first_customer)
    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp).defaultMinSize(minHeight = 136.dp)
            .background(color = AccentLight, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onCardClicked), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(Res.drawable.ic_add_button), contentDescription = null)
            Text(text = emptyTitle, style = appTypography().title15)
        }

    }
}

@Composable
fun HomeTopBar(
    customerName: String = "",
    phoneNumber: String = "",
    avatar: String = "",
    onNavigateToProfile: () -> Unit,
    onNavigateToNotification: () -> Unit,
    onSearchQueryCompleted: (String) -> Unit
) {
    val query = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier.fillMaxWidth().background(
                color = Color.Black,
                shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
            ).padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {


                // Profile Picture
                AsyncImage("$BASE_URL$avatar",contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(60.dp)
                        .background(Color.White, shape = CircleShape)
                        .clickable(onClick = onNavigateToProfile))
                // User Info
                Column(
                    modifier = Modifier.weight(1f)
                        .clickable(onClick = onNavigateToProfile)
                ) {
                    Text(
                        text = customerName,
                        color = Color.White,
                        style = appTypography().title15
                    )
                    Text(
                        text = phoneNumber,
                        color = Color.White,
                        style = appTypography().title15,
                        fontSize = 14.sp
                    )
                }

                // Notification Icon
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .clickable(onClick = onNavigateToNotification),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_notification),
                        contentDescription = "Notifications"
                    )
                    // Dot indicator for unread notifications
                    Box(
                        modifier = Modifier.padding(bottom = 12.dp, start = 12.dp).size(7.dp)
                            .background(color = Color.Red, shape = CircleShape)
                    )
                }
            }
        }

        OutlinedTextFieldModeArt(
            width = 318.dp,
            modifier = Modifier.offset(y = 30.dp).align(Alignment.BottomCenter),
            value = query.value,
            leadingIcon = Res.drawable.ic_search,
            onValueChange = { query.value = it },
            isSearch = true,
            onSearchCompleted = onSearchQueryCompleted ,
            hint = stringResource(Res.string.search_in_customers)
        )
    }


}


@Composable
fun Statistics(allCustomersCount: String, thisMonthCustomer: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Customers count
        Box(
            modifier = Modifier.size(154.dp, 137.dp)
                .background(Color.Black, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier.size(50.dp, 45.dp).background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_user_star),
                        contentDescription = "Notifications"
                    )

                }

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(Res.string.all_customer),
                    style = appTypography().title15.copy(color = Color.White)
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = allCustomersCount,
                    style = appTypography().title15.copy(color = Color.White)
                )
            }
        }

        // This month customer
        Box(
            modifier = Modifier.size(154.dp, 137.dp)
                .background(Accent, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier.size(50.dp, 45.dp).background(
                        Color.Black,
                        RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_target),
                        contentDescription = "Notifications", tint = Color.White
                    )

                }

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(Res.string.this_month_customer),
                    style = appTypography().title15
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = thisMonthCustomer,
                    style = appTypography().title15
                )
            }
        }
    }
}


@Composable
fun CustomerItem(customerProfile: CustomerProfile, onCustomerClicked: (CustomerProfile) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = { onCustomerClicked(customerProfile) })
    ) {
        Box(modifier = Modifier.size(68.dp).clip(shape = RoundedCornerShape(16.dp))) {
            Image(painter = painterResource(Res.drawable.test_avatar), contentDescription = null)
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Name",
            style = appTypography().body14.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = customerProfile.name.toString(),
            style = appTypography().body14.copy(
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        )

    }
}

@Composable
fun LatestCustomerSection(latestCustomers: List<CustomerProfile>, firstCustomer: () -> Unit) {
    if (latestCustomers.isNotEmpty())
        LazyRow {
            items(latestCustomers.size) {
                CustomerItem(latestCustomers[it]) {}
            }
        }
    else
        EmptyNoteOrCustomer(isNote = false) {
            firstCustomer()
        }
}

@Composable
@Preview
fun HomePreview() {
    Column {
//        CustomerItem()
//        Statistics()
//        HomeTopBar()
//        LastNoteItem()
//        EmptyNoteOrCustomer()
    }
}

