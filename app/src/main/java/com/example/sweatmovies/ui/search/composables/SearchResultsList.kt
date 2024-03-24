package com.example.sweatmovies.ui.search.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sweatmovies.ui.composables.HorizontalLoading
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem

@Composable
fun SearchResultsList(
    items: List<SearchResultItem>,
    onItemClicked: (Int) -> Unit
) {
    LazyColumn {
        items(
            count = items.size,
            key = { index -> items[index].id }
        ) { index ->
            when (val item = items[index]) {
                SearchResultItem.Loading -> HorizontalLoading()
                is SearchResultItem.Overview -> SearchResultOverview(item) {
                    onItemClicked.invoke(item.id)
                }
            }
        }
    }
}

@Composable
private fun SearchResultOverview(
    overview: SearchResultItem.Overview,
    onItemClicked: (Int) -> Unit
) {
    Row {
        Image(
            modifier = Modifier
                .size(180.dp, 240.dp)
                .padding(16.dp)
                .clickable { onItemClicked.invoke(overview.id) },
            painter = rememberAsyncImagePainter(overview.photo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(text = overview.name)
    }
}