package com.dreamgrove.android.warcraftlorequiz

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException
import java.lang.StringBuilder

private const val KEY_SCORE = "score"
private const val KEY_QUESTION_COUNT = "questionCount"

class QuizScoreDialogFragment : DialogFragment() {

    private var score: Int = 0
    private var questionCount: Int = 0
    private lateinit var scoreText: StringBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            getArgs()
        }
        else {
            getSavedInstanceKeys(savedInstanceState)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        buildScoreText()

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(scoreText)
                .setNeutralButton(R.string.ok_button_text
                ) { _, _ ->
                    //do nothing but close the dialog box when OK is tapped
                }
            builder.create()
        } ?: throw IllegalStateException("Activity instance cannot be null.")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        setSavedInstanceKeys(outState)
    }

    private fun getArgs() {
        score = arguments?.getInt(KEY_SCORE) ?: 0
        questionCount = arguments?.getInt(KEY_QUESTION_COUNT) ?: 0
    }

    private fun setSavedInstanceKeys(outState: Bundle) {
        outState.apply {
            putInt(KEY_SCORE, score)
            putInt(KEY_QUESTION_COUNT, questionCount)
        }
    }

    private fun getSavedInstanceKeys(savedInstanceState: Bundle?) {
        score = savedInstanceState?.getInt(KEY_SCORE, score) ?: 0
        questionCount = savedInstanceState?.getInt(KEY_QUESTION_COUNT) ?: 0
    }

    private fun buildScoreText() {
        scoreText = StringBuilder("Score: ${score}/${questionCount}")
    }

    companion object {
        fun newInstance(score: Int, questionCount: Int): QuizScoreDialogFragment {
            val quizScoreDialogFragment = QuizScoreDialogFragment()
            val bundle = Bundle()
            bundle.apply {
                putInt(KEY_SCORE, score)
                putInt(KEY_QUESTION_COUNT, questionCount)
            }
            quizScoreDialogFragment.arguments = bundle

            return quizScoreDialogFragment
        }
    }
}