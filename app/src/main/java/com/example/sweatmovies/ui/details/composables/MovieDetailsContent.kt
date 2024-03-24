package com.example.sweatmovies.ui.details.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sweatmovies.R
import com.example.sweatmovies.ui.details.uimodels.DetailsScreenState

@Composable
fun MovieDetailsContent(
    modifier: Modifier,
    state: DetailsScreenState,
    posterClicked: () -> Unit,
    onFavoriteClicked: (Boolean)-> Unit
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clickable { posterClicked() },
            painter = rememberAsyncImagePainter(state.photo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = state.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.add_watch_list_prompt)
        )
        Checkbox(
            checked = state.isFavorite,
            onCheckedChange = { onFavoriteClicked(it) }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MovieDetailsContentPreview() {
    MovieDetailsContent(
        modifier = Modifier.padding(8.dp),
        state = DetailsScreenState(title = "Super Cool Title"),
        posterClicked = {},
        onFavoriteClicked = {}
    )
}