package com.example.sweatmovies.ui.home.tabs.composables

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeTabs

@Composable
fun HomeTabs(
    selectedTab: HomeTabs,
    tabClicked: (HomeTabs) -> Unit
) {
    val tabs = HomeTabs.entries

    TabRow(selectedTabIndex = selectedTab.ordinal) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTab.ordinal == index,
                onClick = {
                    tabClicked(tabs[index])
                },
                text = {
                    Text(
                        text = stringResource(id = tab.title),
                        maxLines = 2
                    )
                }
            )
        }
    }
}