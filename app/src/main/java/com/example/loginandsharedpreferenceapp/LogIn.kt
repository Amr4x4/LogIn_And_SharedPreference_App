package com.example.loginandsharedpreferenceapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LogInScreen(modifier: Modifier = Modifier, viewModel: LogInViewModel = viewModel()) {
    val context = LocalContext.current
    var showSuggestions by remember { mutableStateOf(false) }
    var savedEmails = getEmailsFromPreferences(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = {
                Text(
                    text = "E-mail",
                    color = Color.White
                    )
            },
        )

        Image(
            painter = painterResource(id = R.drawable.arrow_down),
            contentDescription = "Show previous Emails",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    showSuggestions = !showSuggestions
                }
        )

        if (showSuggestions && savedEmails.isNotEmpty()) {
            Column(modifier = Modifier.wrapContentSize()) {
                savedEmails.forEach { email ->
                    Text(
                        text = email,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                viewModel.email = email
                                showSuggestions = false
                            }
                            .padding(8.dp)
                            .background(Color.Gray)
                            .fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = {
                Text(
                    text = "Password",
                    color = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = {
                saveEmailToPreferences(context, viewModel.email)
                Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "LogIn")
        }
    }
}

fun saveEmailToPreferences(context: Context, email: String) {
    val sharedPreferences = context.getSharedPreferences("LogIn Data", Context.MODE_PRIVATE)
    val emails = sharedPreferences.getStringSet("emails", mutableSetOf()) ?: mutableSetOf()
    emails.add(email)
    val editor = sharedPreferences.edit()
    editor.putStringSet("emails", emails)
    editor.apply()
}

fun getEmailsFromPreferences(context: Context): List<String> {
    val sharedPreferences = context.getSharedPreferences("LogIn Data", Context.MODE_PRIVATE)
    return sharedPreferences.getStringSet("emails", mutableSetOf())?.toList() ?: emptyList()
}

@Preview
@Composable
private fun LogInPreview() {
    LogInScreen()
}
