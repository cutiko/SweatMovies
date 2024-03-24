package com.example.sweatmovies.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@Composable
fun BottomMenu(navController: NavController) {
    var selectedItem by remember {
        //start at home by default
        mutableIntStateOf(0)
    }
    val tabs = MainTab.entries
    NavigationBar {
        tabs.forEachIndexed { index, tab ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    navController.navigate(tab.destination.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    selectedItem = index
                },
                icon = {  },
                label = {
                    Text(text = stringResource(id = tab.title))
                }
            )
        }
    }
}