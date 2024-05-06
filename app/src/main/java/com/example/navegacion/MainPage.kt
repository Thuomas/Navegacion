package com.example.navegacion

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navegacion.ui.theme.NavegacionTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(modifier: Modifier = Modifier) {
    val navControler = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navBackStackEntry by navControler.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text(text = "Pagina 1") },
                    selected = currentRoute == "page1",
                    onClick = {
                        navControler.navigate("page1")
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
            NavigationDrawerItem(
                label = { Text(text = "Pagina 2") },
                selected = currentRoute == "page2",
                onClick = {
                    navControler.navigate("page2")
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            )

        },
        drawerState = drawerState
    ) {
        Scaffold(modifier = modifier,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(imageVector =  Icons.Filled.Menu,
                                contentDescription = "Boton Hamburguesa")
                        }
                    },
                    title = {
                        Text(text = "Cualquier cosa")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) {
            NavHost(
                navController = navControler,
                modifier = Modifier.padding(it),
                startDestination = "page1"
            ) {
                composable("page1") { Page1(navController = navControler) }
                composable("page2") { Page2(navController = navControler) }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    NavegacionTheme {
        MainPage()
    }
}