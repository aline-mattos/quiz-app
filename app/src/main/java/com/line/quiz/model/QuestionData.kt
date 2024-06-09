package com.line.quiz.model

data class QuestionData(
    val title: String,
    val imageUrl: String,
    val answers: List<String>,
    val correctAnswer: String
)
