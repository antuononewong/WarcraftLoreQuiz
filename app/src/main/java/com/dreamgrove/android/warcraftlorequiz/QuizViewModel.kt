package com.dreamgrove.android.warcraftlorequiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val loreQuestions: List<LoreQuestion> = listOf(
        LoreQuestion(R.string.lore_question_thrall, "doomhammer"),
        LoreQuestion(R.string.lore_question_jaina, "frost"),
        LoreQuestion(R.string.lore_question_sylvanas, "sylvanas windrunner"),
        LoreQuestion(R.string.lore_question_anduin, "little lion"),
        LoreQuestion(R.string.lore_question_arthas, "arthas menethil"),
        LoreQuestion(R.string.lore_question_bwonsamdi, "bwonsamdi"),
        LoreQuestion(R.string.lore_question_windrunner, "three"),
        LoreQuestion(R.string.lore_question_kalecgos, "kalecgos"),
        LoreQuestion(R.string.lore_question_varian, "varian wrynn")
    )

    private val mutableEndOfQuestions: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    private val endOfQuiz: Boolean
        get() = currentQuestionIndex == questionCount - 1

    private val startOfQuiz: Boolean
        get() = currentQuestionIndex == 0

    val questionCount: Int
        get() = loreQuestions.size

    val currentQuestion: Int
        get() = loreQuestions[currentQuestionIndex].resId

    val correctAnswer: Boolean
        get() = answer == loreQuestions[currentQuestionIndex].answer

    val endQuiz: LiveData<Boolean> = mutableEndOfQuestions
    var currentQuestionIndex: Int = 0
    var currentScore: Int = 0
    var answer: String = ""
    var answeredPreviousCorrectly: Boolean = false

    fun nextQuestion() {
        if (endOfQuiz) {
            endQuiz()
            return
        }
        currentQuestionIndex += 1
    }

    fun previousQuestion() {
        if (startOfQuiz)
            return

        if (answeredPreviousCorrectly) {
            adjustScore(-1)
            answeredPreviousCorrectly = false
        }

        currentQuestionIndex -= 1
    }

    fun checkAnswer() {
        if (correctAnswer) {
            adjustScore(1)
            answeredPreviousCorrectly = true
        }
    }

    fun restartQuiz() {
        currentQuestionIndex = 0
        currentScore = 0
        answeredPreviousCorrectly = false
        mutableEndOfQuestions.value = false
    }

    private fun adjustScore(points: Int) {
        currentScore += points
    }

    private fun endQuiz() {
        mutableEndOfQuestions.value = true
    }

}