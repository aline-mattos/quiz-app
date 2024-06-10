package com.line.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.line.quiz.model.QuestionData
import kotlinx.coroutines.delay

@Composable
fun QuestionScreen(
    question: QuestionData,
    playerName: String,
    onAnswerSelected: (Boolean, Int) -> Unit // Boolean indicates if the answer is correct
) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var shuffledAnswers by remember { mutableStateOf(question.answers.shuffled()) }
    var timeLeft by remember { mutableStateOf(10) }
    var isTimeUp by remember { mutableStateOf(false) }

    LaunchedEffect(isTimeUp) {
        if (isTimeUp) {
            onAnswerSelected(false, 0)
        }
    }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        }
        isTimeUp = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Blue, MaterialTheme.colorScheme.primary)
                )
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = question.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = question.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tempo restante: $timeLeft segundos",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    shuffledAnswers.take(2).forEach { answer ->
                        AnswerButton(
                            text = answer,
                            isSelected = selectedAnswer == answer,
                            correctAnswer = question.correctAnswer,
                            onClick = {
                                if (!isTimeUp) {
                                    selectedAnswer = answer
                                    val points = if (timeLeft > 0) timeLeft else 0
                                    val isCorrect = answer == question.correctAnswer
                                    onAnswerSelected(isCorrect, points)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    shuffledAnswers.takeLast(2).forEach { answer ->
                        AnswerButton(
                            text = answer,
                            isSelected = selectedAnswer == answer,
                            correctAnswer = question.correctAnswer,
                            onClick = {
                                if (!isTimeUp) {
                                    selectedAnswer = answer
                                    val points = if (timeLeft > 0) timeLeft else 0
                                    val isCorrect = answer == question.correctAnswer
                                    onAnswerSelected(isCorrect, points)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerButton(text: String, isSelected: Boolean, correctAnswer: String, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected && text == correctAnswer -> Color.Green
        isSelected && text != correctAnswer -> Color.Red
        else -> Color.Blue
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(150.dp) // Make it a square button
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
