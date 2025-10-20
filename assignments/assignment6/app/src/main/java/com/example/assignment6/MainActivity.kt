//your package name here

package com.example.assignment6

// Core Android imports
import android.net.Credentials
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navigation imports
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * This project covers concepts from Chapter 9 lessons:
 * - "Introduction to Navigation" - for setting up basic navigation structure
 * - "Passing Arguments" - for passing user data between screens
 * - "Managing the Back Stack" - for controlling navigation flow
 * - "Navigation UI Components" - for creating user interface elements
 * - "Organizing Navigation with Multiple Files" - for project structure
 *
 * Students should review these lessons before starting:
 * - Introduction to Navigation lesson for basic navigation setup
 * - Passing Arguments lesson for data transfer between screens
 * - Managing the Back Stack lesson for navigation control
 * - Navigation UI Components lesson for UI design
 * - Organizing Navigation with Multiple Files lesson for project organization
 */

/**
 * MainActivity is the entry point of the application.
 * It sets up the basic window configuration and initializes the Compose UI with navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure the window to use light status bar icons
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Set up the Compose UI with navigation
        setContent {
            MaterialTheme {
                Surface {
                    LoginApp()
                }
            }
        }
    }
}

/**
 * Main navigation app that handles the login flow
 */
@Composable
fun LoginApp() {
    val navController   = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onLoginSuccess = { userName ->
                navController.navigate("welcome/$userName") {
                }
            })
        }
        composable(
            "welcome/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            if (userName != null) {
                WelcomeScreen(
                    userName = userName,
                    onViewProfile = {navController.navigate("profile/$userName") },
                    onLogout = {navController.navigate("login")}
                )
            }
        }

        composable("profile/{userName}",
            arguments = listOf(
                navArgument("userName") {type = NavType.StringType}
            )
        ) {backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            if (userName != null) {
                ProfileScreen(
                    userName = userName,
                    onBackToWelcome = {navController.navigate("welcome/$userName")},

                    )
            }
        }


    }
}

/**
 * Login screen with validation using Chapter 7 concepts
 */
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var validEmailBool by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }

    val validEmail = "student@wccnet.edu"
    val validPassword = "password123"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Student Login", style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold)
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    isError = nameError,
                    supportingText = {if(nameError)Text("Invalid name")},
                    leadingIcon = {Icon(imageVector = Icons.Default.Person, contentDescription = "")},
                    label = {Text("Full Name")}

                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),

                    isError = nameError && !validEmailBool,
                    supportingText = {if(nameError && !validEmailBool)Text("Invalid email")},
                    leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = "")},
                    label = {Text("Email")}
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done),

                    isError = passwordError && password.isNotEmpty(),
                    supportingText = {if(passwordError)Text("Invalid password")},
                    leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = "")},
                    trailingIcon = {
                        IconButton(onClick = {passwordVisibility = ! passwordVisibility}) {
                            if(passwordVisibility) Text("Hide",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary)
                            else Text("Show",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                                )
                        }

                    },
                    label = {Text("Password")},
                    visualTransformation = if(!passwordVisibility) PasswordVisualTransformation()
                    else VisualTransformation.None
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (name.isEmpty()) nameError = true else nameError = false
                    if(email != validEmail) validEmailBool = false else validEmailBool = true
                    if(!isValidEmail(email)) emailError = true else emailError = false
                    if(password != validPassword) passwordError = true else passwordError = false
                    if(!nameError && !emailError && validEmailBool && !passwordError)
                        onLoginSuccess(name.trim())

                },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = ("Login"))
                }

                Text(text = "Demo: $validEmail / $validPassword",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Light)
            }
        }
    }
}

/**
 * Welcome screen that displays after successful login
 */
@Composable
fun WelcomeScreen(
    userName: String,
    onViewProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome!", style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold)
        Text(text = "Hello, $userName!", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = {onViewProfile()}){
            Text(text = "View Profile")
        }
        Button(onClick = {onLogout()}){
            Text(text = "Logout")
        }
    }
}

/**
 * Profile screen showing user information
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String,
    onBackToWelcome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text("User Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    )
                }
            )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {


            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileRow("Name", "\t\t$userName")
                ProfileRow("Email", "\t\tstudent@wccnet.edu")
                ProfileRow("Student ID", "\t\t2024001")
                ProfileRow("Major", "\t\tComputer Science")
                ProfileRow("Year", "\t\t\tFreshman")
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {onBackToWelcome()}) {
            Text("Back to Welcome", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}

/**
 * Helper composable for profile information rows
 */
@Composable
fun ProfileRow(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            )
        Text(value,
            style = MaterialTheme.typography.bodyLarge)
    }
}

/**
 * Email validation function using regex (Chapter 7: Regular Expressions)
 */
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return email.matches(emailRegex)
}

/**
 * Preview function for the login screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {})
    }
}

/**
 * Preview function for the welcome screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            userName = "John Doe",
            onViewProfile = {},
            onLogout = {}
        )
    }
}

/**
 * Preview function for the profile screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            userName = "John Doe",
            onBackToWelcome = {}
        )
    }
}
