package com.example.assignment5

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.tooling.preview.Preview



/**
 * This project covers concepts from Chapter 8 lessons:
 * - "Lazy Column" - for creating scrollable contact lists
 * - "Handling Clicks and Selection" - for interactive contact selection
 * - "Combining LazyColumn and LazyRow" - for understanding list composition
 *
 * Students should review these lessons before starting:
 * - LazyColumn lesson for list implementation
 * - Clicks and Selection lesson for interactive behavior
 * - Combined lesson for understanding how lists work together
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactListApp()
                }
            }
        }
    }
}




@Composable
fun ContactListApp() {
    var tempString by remember {mutableStateOf("")}
    fun contactGen(num: Int): String {
        tempString = if(tempString == "555-010${num}" && num <= 9)
            "555-010${num+1}"
        else if(tempString == "555-010${num}" && num > 9)
            "555-01${num}"
        else if(num <= 9)
            "555-010${num}"
        else
            "555-01${num}"
        return tempString
    }
    val contacts = listOf(
        Contact("John Doe", "johnemail@example.com", contactGen(1),selected = false ),
        Contact("Jane Doe","janeemail@example.com", contactGen(2),selected = false ),
        Contact("John Doe", "johnemail@example.com", contactGen(3), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(4), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(5), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(6), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(7), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(8), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(9), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(10), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(11), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(12), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(13), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(14), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(15), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(16), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(17), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(18), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(19), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(20), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(21), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(22), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(23), selected = false),
        Contact("Jane Doe","janeemail@example.com", contactGen(24), selected = false),
        Contact("John Doe", "johnemail@example.com", contactGen(25), selected = false)
    )
    ContactList(contacts)
}

@Composable
fun ContactList(contacts: List<Contact>) {
    var selectedContact by remember {mutableStateOf<Contact?>(null)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {
        Text(
            text = "Contact List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            if(selectedContact != null)"Selected: ${selectedContact?.name}"
            else "No contact selected",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) {contact ->
                if(!contact.name.isEmpty() && !contact.email.isEmpty() && !contact.phone.isEmpty())
                   ContactItem(
                       contact = contact,
                       isSelected =  selectedContact != null && contact.selected,
                       onClick = {
                            if(selectedContact != null && contact.selected) {
                                selectedContact = null
                                contact.selected = false
                            } else {
                                selectedContact = contact
                                contact.selected = true
                            }
                       }
                   )
                contact.selected = false
            }

        }
        if(selectedContact != null) {
            Button(
                onClick = {
                    selectedContact = null
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            ) {
                Text("Clear Selection")
            }

    }
        }
}

@Composable
fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(50.dp)
                    .background(
                        color = if(isSelected)
                            MaterialTheme.colorScheme.background
                        else
                            MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${contact.name.first()}")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = if(isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Black
                )
                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if(isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Black
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = if(isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Black
                )
            }
            if(isSelected)
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
        }
    }
}

// Data class for contact information
data class Contact(
    val name: String,
    val email: String,
    val phone: String,
    var selected : Boolean
)

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactListAppPreview() {
    ContactListApp()
}

