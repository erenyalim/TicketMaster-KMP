package org.example.ticketmaster.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.ticketmaster.presentation.home.HomeUiState
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.Res
import ticketmaster.shared.generated.resources.search_hint

@Composable
fun ExploreBoxSection(
    uiState: HomeUiState,
    onSearchTextChanged: (String) -> Unit,
    onFilterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchBg = Color(0xFF1C2129)
    val accentColor = Color(0xFF00E5FF)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(searchBg)
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (uiState.searchText.isEmpty()) {
                        Text(
                            text = stringResource(Res.string.search_hint),
                            color = Color.Gray.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                    BasicTextField(
                        value = uiState.searchText,
                        onValueChange = onSearchTextChanged,
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        ),
                        cursorBrush = SolidColor(accentColor),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(searchBg)
                .border(
                    width = 1.dp,
                    color = accentColor.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onFilterClicked() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = "Filter",
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
