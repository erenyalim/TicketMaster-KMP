package org.example.ticketmaster.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.example.ticketmaster.domain.model.Banner

@Composable
fun BannerSection(
    banners: List<Banner>,
    modifier: Modifier = Modifier,
    onBannerClick: ((Banner) -> Unit)? = null,
    autoScrollIntervalMs: Long = 3500L
) {
    if (banners.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { banners.size })

    LaunchedEffect(pagerState, banners.size) {
        if (banners.size > 1) {
            while (isActive) {
                delay(autoScrollIntervalMs)
                if (!pagerState.isScrollInProgress) {
                    val nextPage = (pagerState.currentPage + 1) % banners.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val banner = banners[page]
            BannerCardItem(
                banner = banner,
                pageIndex = page,
                onBannerClick = onBannerClick
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        BannerPagerIndicator(
            itemCount = banners.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
private fun BannerCardItem(
    banner: Banner,
    pageIndex: Int,
    onBannerClick: ((Banner) -> Unit)?
) {
    val searchBoxBackground = Color(0xFF1C2129)
    val imageUrl = banner.formattedImageUrl
    val clickableModifier = if (onBannerClick != null) {
        Modifier.clickable { onBannerClick(banner) }
    } else Modifier

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(searchBoxBackground)
            .then(clickableModifier)
    ) {
        if (imageUrl.isNotBlank()) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = banner.title.ifBlank { "Banner $pageIndex" },
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun BannerPagerIndicator(
    itemCount: Int,
    currentPage: Int
) {
    val buttonColor = Color(0xFF00E5FF)
    val inactiveDotColor = Color(0xFF30343D)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(itemCount) { iteration ->
            val isSelected = currentPage == iteration
            val color = if (isSelected) buttonColor else inactiveDotColor
            val width = if (isSelected) 24.dp else 8.dp

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(width = width, height = 8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
        }
    }
}
