package com.example.sweatmovies.ui.search.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(userInput: String, onChange: (String) -> Unit) {
    TextField(
        modifier = Modifier.padding(24.dp),
        value = userInput,
        onValueChange = onChange
    )
}