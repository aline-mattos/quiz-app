package com.line.quiz


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.line.quiz.model.QuestionData
import com.line.quiz.model.LeaderboardEntry
import com.line.quiz.repositories.LeaderboardRepository


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
        QuestionData(
            title = "De qual estação de trem sai o Expresso de Hogwarts?",
            imageUrl = "https://turismo.eurodicas.com.br/wp-content/uploads/2019/12/kings-cross-station-1.jpg",
            answers = listOf("King's Cross", "Marleybone", "Paddington", "Euston"),
            correctAnswer = "King's Cross"
        ),
        QuestionData(
            title = "Qual feitiço Hermione ajudou Rony a aprender?",
            imageUrl = "https://pbs.twimg.com/media/FEbXS5TXMAEOgB8.jpg",
            answers = listOf("Wingardium Leviosa", "Expelliarmus", "Estupefaça", "Riddikulus"),
            correctAnswer = "Wingardium Leviosa"
        ),
        QuestionData(
            title = "Qual é a profissão dos pais trouxas da Hermione?",
            imageUrl = "https://pa1.aminoapps.com/6630/175e60baa1462733a7bdb233632c2b03ad7caaec_00.gif",
            answers = listOf("Artistas", "Médicos", "Dentistas", "Professores"),
            correctAnswer = "Dentistas"
        ),
        QuestionData(
            title = "Qual o símbolo da casa Corvinal?",
            imageUrl = "https://allandevery.co.uk/cdn/shop/products/PBWBHPR0090-SAPP-ZOOM.jpg?v=1686924455",
            answers = listOf("Corvo", "Águia", "Coruja", "Falcão"),
            correctAnswer = "Águia"
        )
    ).shuffled()

    val context = LocalContext.current
    var correctAnswersCount by remember { mutableStateOf(0) }
    var playerName by remember { mutableStateOf("") }
    var points by remember { mutableStateOf(0) }
    val leaderboardRepository = remember { LeaderboardRepository(context = context) }

    fun navigateToNextQuestionOrResults(index: Int, isCorrect: Boolean, pointsEarned: Int) {
        if (isCorrect) {
            correctAnswersCount++
        }
        points += pointsEarned
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
                correctAnswersCount = 0
                points = 0
                navController.navigate("question0")
            },
                onLeaderboardClicked = {
                    navController.navigate("leaderboard")
                }
            )
        }
        questions.forEachIndexed { index, question ->
            composable("question$index") {
                QuestionScreen(
                    question = question,
                    playerName = playerName,
                    onAnswerSelected = { isCorrect, pointsEarned ->
                        navigateToNextQuestionOrResults(index, isCorrect, pointsEarned)
                    }
                )
            }
        }
        composable("results/{correctAnswersCount}/{points}") { backStackEntry ->
            val correctAnswers = backStackEntry.arguments?.getString("correctAnswersCount")?.toIntOrNull() ?: 0
            val points = backStackEntry.arguments?.getString("points")?.toIntOrNull() ?: 0
            ResultsScreen(navController, playerName, correctAnswers, points, leaderboardRepository)
        }
        composable("leaderboard") {
            LeaderboardScreen(navController, leaderboardRepository)
        }
    }
}
