package org.example.ticketmaster.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.ticketmaster.presentation.home.components.BannerSection
import org.example.ticketmaster.presentation.home.components.ExploreBoxSection
import org.example.ticketmaster.presentation.home.components.FeaturedEventsSection
import org.example.ticketmaster.presentation.home.components.FilterBottomSheetContent
import org.jetbrains.compose.resources.painterResource
import ticketmaster.shared.generated.resources.Res
import ticketmaster.shared.generated.resources.tm_logo
import ticketmaster.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchTextChanged: (String) -> Unit,
    onExploreClicked: () -> Unit,
    onNotificationsClicked: () -> Unit,
    onEventClick: (org.example.ticketmaster.domain.model.Event) -> Unit
) {
    val scrollState = rememberScrollState()
    val darkBackground = Color(0xFF14171D)
    val accentColor = Color(0xFF00E5FF)

    var showFilterSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetState,
            containerColor = Color(0xFF1C2129),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
        ) {
            FilterBottomSheetContent(
                uiState = uiState,
                onDateSelected = { },
                onCategorySelected = { },
                onApplyClicked = {
                    showFilterSheet = false
                    onExploreClicked()
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { }
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = stringResource(Res.string.home_location),
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                    Text(
                        text = uiState.selectedCity,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Image(
                painter = painterResource(Res.drawable.tm_logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(20.dp)
            )

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1C2129))
                    .align(Alignment.CenterEnd)
                    .clickable { onNotificationsClicked() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        ExploreBoxSection(
            uiState = uiState,
            onSearchTextChanged = onSearchTextChanged,
            onFilterClicked = { showFilterSheet = true }
        )

        Spacer(modifier = Modifier.height(32.dp))

        BannerSection(banners = uiState.banners)

        Spacer(modifier = Modifier.height(32.dp))

        FeaturedEventsSection(
            events = uiState.featuredEvents,
            onEventClick = onEventClick
        )

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14171D)
@Composable
fun HomeScreenPreview() {
    val dummyState = HomeUiState(
        searchText = "",
        selectedCountry = "Turkey",
        selectedCity = "Istanbul",
        selectedDate = "This Weekend",
        selectedCategory = "Music",
        banners = emptyList()
    )

    HomeScreen(
        uiState = dummyState,
        onSearchTextChanged = {},
        onExploreClicked = {},
        onNotificationsClicked = {},
        onEventClick = {}
    )
}