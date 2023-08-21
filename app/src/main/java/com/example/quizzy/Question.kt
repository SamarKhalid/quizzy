package com.example.quizzy

data class Question(
    val id : Int,
    val question : String,
    val img : Int,
    val optionOne : String,
    val optionTwo : String,
    val optionThree : String,
    val optionFour : String,
    val correctAnswer : Int
    )
