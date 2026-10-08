package org.example.ticketmaster.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.ticketmaster.presentation.home.HomeUiState
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.*

@Composable
fun FilterBottomSheetContent(
    uiState: HomeUiState,
    onDateSelected: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onApplyClicked: () -> Unit
) {
    val darkBackground = Color(0xFF14171D)
    val accentColor = Color(0xFF00E5FF)

    val dateOptions = listOf(
        stringResource(Res.string.filter_date_today), 
        stringResource(Res.string.filter_date_tomorrow), 
        stringResource(Res.string.filter_date_this_week), 
        stringResource(Res.string.filter_date_this_month)
    )
    val categoryOptions = listOf(
        stringResource(Res.string.filter_cat_music), 
        stringResource(Res.string.filter_cat_sports), 
        stringResource(Res.string.filter_cat_arts), 
        stringResource(Res.string.filter_cat_family), 
        stringResource(Res.string.filter_cat_comedy)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(Res.string.filter_title),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(text = stringResource(Res.string.filter_date), color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(dateOptions) { date ->
                CustomChip(
                    text = date,
                    isSelected = uiState.selectedDate == date,
                    onClick = { onDateSelected(date) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(Res.string.filter_category), color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categoryOptions) { category ->
                CustomChip(
                    text = category,
                    isSelected = uiState.selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = accentColor, shape = RoundedCornerShape(16.dp))
                .clickable { onApplyClicked() },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(Res.string.apply_filters),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = darkBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CustomChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val accentColor = Color(0xFF00E5FF)
    val inactiveBg = Color(0xFF1C2129)
    val inactiveBorder = Color(0xFF292E37)

    val bgColor = if (isSelected) accentColor else inactiveBg
    val textColor = if (isSelected) Color(0xFF14171D) else Color.White

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .then(if (!isSelected) Modifier.border(1.dp, inactiveBorder, RoundedCornerShape(20.dp)) else Modifier)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}