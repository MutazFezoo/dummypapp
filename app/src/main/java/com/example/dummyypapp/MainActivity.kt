
package com.example.dummyypapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dummyypapp.ui.theme.DummyypappTheme
import com.example.dummyypapp.viewModel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dummyypapp.model.UserDataItem
import coil.compose.AsyncImage
import com.example.dummyypapp.ui.theme.header
import com.example.dummyypapp.ui.theme.labelSmall
import com.example.dummyypapp.ui.theme.titleLarge

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<UsersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            setUpNavHost(navController,viewModel)
           // UsersScreen(navController,viewModel)
           /* DummyypappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }*/
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DummyypappTheme {
        Greeting("Android")
    }
}

@Composable
fun UsersScreen(navController: NavHostController,viewModel: UsersViewModel) {
    val users = viewModel.users.value
    //val users = viewModel.users

    Column(
        modifier = Modifier.background(color = Color(0xFFE4E4E4)),

        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (users.isEmpty()) {
                Box(modifier = Modifier.fillMaxHeight()
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text("Loading... ")
                }

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    items(users, key = { it.id!! }){
                        UserItem(it,navController)
                    }

                }


            }
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavHostController, id: String?,viewModel: UsersViewModel) {
    viewModel.getUserById(id = "$id")
    val user = viewModel.user.value
        if(user == null) {

            Text("Loading... ")

        }else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                TopAppBar(
                    title = { Text(text = "") },
                    actions = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Back",
                            )
                        }
                    }

                )
                AsyncImage(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .clip(
                            shape = RoundedCornerShape(16.dp,)
                        ),
                    model = "${user.picture}",
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    error = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "The delasign logo",
                )
                Row {
                    user.firstName?.let { Text("$it ",
                        style = titleLarge) }
                    user.lastName?.let { Text(it,
                        style = titleLarge) }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Divider()
                Spacer(modifier = Modifier.height(20.dp))
                Text("Contacts",style = header)
                user.phone?.let { Text(it,
                    style = labelSmall
                    ) }
                user.email?.let { Text(it ,
                    style = labelSmall
                ) }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Address",style = header)
                user.location?.country?.let { Text(it,
                    style = labelSmall
                ) }
                user.location?.street?.let { Text(it,
                    style = labelSmall
                ) }
            }


        }



}


@Composable
fun UserItem(user: UserDataItem,navController: NavHostController) {
    Card(
        modifier = Modifier.background(color = Color.White),
    ) {

        Row(
            modifier = Modifier.background(color = Color.White),

            ){
            AsyncImage(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                model = "${user.picture}",
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "The delasign logo",
            )
           Column(modifier = Modifier
               .fillMaxSize()
               .padding(16.dp)
               .clickable(
                   onClick = {
                       navController.navigate("user/${user.id}")
                   }
               )
             //  .background(Color.DarkGray)
           ) {
               user.firstName?.let { Text(it,
                   style = titleLarge) }
               user.lastName?.let { Text(it,
                   style = labelSmall) }
           }
        }
       
    }
}

@Composable
fun setUpNavHost(navController: NavHostController,viewModel: UsersViewModel) {
    NavHost(navController = navController, startDestination = "users") {
        composable("users") { UsersScreen(navController,viewModel) }

        composable("user/{userId}") { backStackEntry ->
            SecondScreen(navController, backStackEntry.arguments?.getString("userId"),viewModel)
        }

    }
}