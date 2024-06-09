package com.line.quiz


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.line.quiz.model.QuestionData


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val questions = listOf(
        QuestionData(
            title = "Qual é a cor normalmente relacionada à casa Grifinória?",
            imageUrl = "https://miro.medium.com/v2/resize:fit:1200/1*g7nlreAAvIeBMGjS9jsRWQ.jpeg",
            answers = listOf("Vermelho", "Verde", "Azul", "Amarelo"),
            correctAnswer = "Vermelho"
        ),
        QuestionData(
            title = "Qual filho dos Weasley é o mais velho?",
            imageUrl = "https://static.wikia.nocookie.net/harrypotterfanon/images/d/dc/Weasley_Family.jpg/revision/latest?cb=20150903225433",
            answers = listOf("Carlinhos", "Percy", "Gui", "Rony"),
            correctAnswer = "Gui"
        ),
        QuestionData(
            title = "Qual era o nome da cobra do Voldemort?",
            imageUrl = "https://uploads.jovemnerd.com.br/wp-content/uploads/2018/09/nagini-harry-potter.jpg",
            answers = listOf("Nagini", "Rabicho", "Salazar", "Marvolo"),
            correctAnswer = "Nagini"
        ),
        QuestionData(
            title = "O que você daria para ajudar alguém atacado por um dementador?",
            imageUrl = "https://monsterlegacy.net/wp-content/uploads/2017/03/dementortrainresiz.jpg",
            answers = listOf("Cerveja Amanteigada", "Uma poção", "Bezoar", "Chocolate"),
            correctAnswer = "Chocolate"
        ),
        QuestionData(
            title = "Qual o nome da coruja do Harry Potter?",
            imageUrl = "https://ew.com/thmb/8vn9k9DU1PsyqG-v6uYEtklnnds=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/harryhedwig_0-d3cdc27a7efb4da286b956872fdfe1f5.jpg",
            answers = listOf("Errol", "Fawkes", "Perebas", "Edwiges"),
            correctAnswer = "Edwiges"
        ),
        QuestionData(
            title = "Qual destas NÃO é uma moeda de bruxo?",
            imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEivD6DSw-hmCFPiIUesNC6_u5NlbPv56R45x_H2TXLCMuUXysWFtfbMhUxF6zbNErHWhXKSjD4B5XA-CXf-YCgQEZNs2I61jJdTcH4Py5psxbBxPBqT1aaUVw3FBvXpyI6_1it4Ywj_Sv4/s1600/GALEAO.jpg",
            answers = listOf("Galeões", "Tornéis", "Sicles", "Nuques"),
            correctAnswer = "Tornéis"
        ),
        // Add more questions here
    ).shuffled()


    var correctAnswersCount by remember { mutableStateOf(0) }
    var playerName by remember { mutableStateOf("") }
    var points by remember { mutableStateOf(0) }


    fun navigateToNextQuestionOrResults(index: Int, isCorrect: Boolean, points: Int) {
        if (isCorrect) {
            correctAnswersCount++
        }
        if (index + 1 < questions.size) {
            navController.navigate("question${index + 1}")
        } else {
            navController.navigate("results/$correctAnswersCount/$points")
        }
    }


    NavHost(
        navController = navController,
        startDestination = "initial",
        modifier = modifier
    ) {
        composable("initial") {
            InitialScreen(onStartQuiz = { name: String ->
                playerName = name
                navController.navigate("question0")
            })
        }
        questions.forEachIndexed { index, question ->
            composable("question$index") {
                QuestionScreen(
                    question = question,
                    playerName = playerName,
                    onAnswerSelected = { isCorrect, points ->
                        navigateToNextQuestionOrResults(index, isCorrect, points)
                    }
                )
            }
        }
        composable("results/{correctAnswersCount}/{points}") { backStackEntry ->
            val correctAnswers = backStackEntry.arguments?.getInt("correctAnswersCount") ?: 0
            val points = backStackEntry.arguments?.getInt("points") ?: 0
            ResultsScreen(playerName, correctAnswers, points)
        }
    }
}
