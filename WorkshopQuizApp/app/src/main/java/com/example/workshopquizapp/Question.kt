package com.example.workshopquizapp

import androidx.annotation.StringRes

data class Question (
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

