package com.example.sweatmovies.ui.home.carrousel.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem

@Composable
fun MovieCarrouselItem(movie: PopularCarrouselItem.Movie, onItemClicked: () -> Unit) {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(180.dp, 240.dp)
                .padding(16.dp)
                .clickable { onItemClicked.invoke() },
            painter = rememberAsyncImagePainter(
                movie.photo
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.align(Alignment.BottomStart),
            text = movie.position.toString(),
            fontSize = 34.sp
        )
    }
}