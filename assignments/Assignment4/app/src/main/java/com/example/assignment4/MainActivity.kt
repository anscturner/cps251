package com.example.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This project covers concepts from Chapter 7 lessons:
 * - "Validation" - for form validation and error handling
 * - "Managing Input State" - for state management in forms
 * - "Text Fields" - for input field styling and error states
 * - "Regular Expressions" - for email, phone, and ZIP code validation
 *
 * Students should review these lessons before starting:
 * - Validation lesson for form validation patterns
 * - Managing Input State lesson for state management
 * - Text Fields lesson for input field styling
 * - Regular Expressions lesson for validation patterns
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactValidatorApp()
            }
        }
    }
}

@Composable
fun ContactValidatorApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ContactForm()
    }
}

@Composable
fun ContactForm() {
    var name by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var phone by remember {mutableStateOf("")}
    var zipCode by remember {mutableStateOf("")}
    
    var isNameValid by remember{mutableStateOf(true)}
    var isEmailValid by remember{mutableStateOf(true)}
    var isPhoneValid by remember{mutableStateOf(true)}
    var isZipValid by remember{mutableStateOf(true)}
    var submittedInfo by remember{mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 16.dp,
        vertical = 16.dp
    ),
    verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        NameField(name, isNameValid = isNameValid, onValueChange = {name = it
        isNameValid = it.isEmpty() || it.matches(validateName)})

        EmailField(email, isEmailValid = isEmailValid, onValueChange = {email = it
        isEmailValid = it.isEmpty() || it.matches(validateEmail)})

        PhoneField(phone, isPhoneValid = isPhoneValid, onValueChange = {phone = it
        isPhoneValid = it.isEmpty() || it.matches(validatePhone)})

        ZipCodeField(zipCode, isZipValid = isZipValid, onValueChange = {zipCode = it
        isZipValid = it.isEmpty() || it.matches(validateZipCode)})

        Button(
            onClick = {
                submittedInfo = "Name: ${name}\nEmail Address: ${email}\nPhone Number: ${phone}\n" +
                        "Zip Code: $zipCode"
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isNameValid && isZipValid && isEmailValid && isPhoneValid
                    &&name.isNotEmpty()&&zipCode.isNotEmpty()&&email.isNotEmpty()
                    &&phone.isNotEmpty()
        ) {
            Text("Submit")
        }
        if(!submittedInfo.isEmpty())
            Text(submittedInfo)
        }
}

@Composable
fun NameField(
    name: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onValueChange,
        label = { Text("Full Name") },
        isError = !isNameValid
    )


}

@Composable
fun EmailField(
    email: String,
    isEmailValid: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("Email Address") },
        isError = !isEmailValid && email.isNotEmpty()
    )
}

@Composable
fun PhoneField(
    phone: String,
    isPhoneValid: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        onValueChange = onValueChange,
        label = { Text("Phone Number") },
        isError = !isPhoneValid && phone.isNotEmpty()
    )
}

@Composable
fun ZipCodeField(
    zipCode: String,
    isZipValid: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = zipCode,
        onValueChange = onValueChange,
        label = { Text("ZIP Code") },
        isError = !isZipValid && zipCode.isNotEmpty()
    )
}


    val validateEmail = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
    val validatePhone = "^\\d{3}-\\d{3}-\\d{4}$".toRegex()
    val validateZipCode = "^\\d{5}(-\\d{4})?\$".toRegex()
    val validateName = "^[A-Za-z\\s]{2,}$".toRegex()

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactValidatorAppPreview() {
    ContactValidatorApp()
}