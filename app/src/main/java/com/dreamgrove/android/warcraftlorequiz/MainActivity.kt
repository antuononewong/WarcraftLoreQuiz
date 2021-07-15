package com.dreamgrove.android.warcraftlorequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import java.util.*

private const val TAG_QUIZ_SCORE_DIALOG = "QuizScoreDialogFragment"

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by viewModels()
    private val loreAnswerListeners: LoreAnswerListeners by lazy {
        LoreAnswerListeners()
    }

    private lateinit var loreQuestionTextView: TextView
    private lateinit var loreAnswerTextBox: TextInputEditText
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setWidgets()
        updateQuestion()

        previousButton.setOnClickListener {
            goPreviousQuestion()
        }

        nextButton.setOnClickListener {
            goNextQuestion()
        }

        loreAnswerTextBox.apply {
            addTextChangedListener(loreAnswerListeners)
            setOnKeyListener(loreAnswerListeners)
        }

        quizViewModel.endQuiz.observe(this, {
            showDialogWindow()
        })

    }

    private fun setWidgets() {
        loreQuestionTextView = findViewById(R.id.lore_question_text)
        loreAnswerTextBox = findViewById(R.id.lore_answer_text_box)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
    }

    private fun goNextQuestion() {
        checkAnswer()
        quizViewModel.nextQuestion()
        updateQuestion()
    }

    private fun goPreviousQuestion() {
        quizViewModel.previousQuestion()
        updateQuestion()
    }

    private fun updateQuestion() {
        loreQuestionTextView.setText(quizViewModel.currentQuestion)
        loreAnswerTextBox.setText("")
    }

    private fun checkAnswer() {
        quizViewModel.checkAnswer()
        if (quizViewModel.correctAnswer) {
            ToastMaker.make(this, "Correct!")
        }
        else {
            ToastMaker.make(this, "Incorrect!")
        }
    }

    private fun showDialogWindow() {
        if (quizViewModel.endQuiz.value == true) {
            val quizScoreDialogFragment = QuizScoreDialogFragment.newInstance(quizViewModel.currentScore, quizViewModel.questionCount)
            quizScoreDialogFragment.show(supportFragmentManager, TAG_QUIZ_SCORE_DIALOG)
            quizViewModel.restartQuiz()
        }
    }

    private inner class LoreAnswerListeners: TextWatcher, View.OnKeyListener {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            quizViewModel.answer = s.toString().lowercase(Locale.getDefault())
        }

        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event?.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                ToastMaker.make(this@MainActivity, "Is that your final answer?\n\tTap next to continue.")
            }
            return false // false closes keyboard
        }

    }
}