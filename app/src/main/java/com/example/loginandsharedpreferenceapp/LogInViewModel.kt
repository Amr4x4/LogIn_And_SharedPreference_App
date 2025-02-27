package com.example.loginandsharedpreferenceapp

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LogInViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
}
