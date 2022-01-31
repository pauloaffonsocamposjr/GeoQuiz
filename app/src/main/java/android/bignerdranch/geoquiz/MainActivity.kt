package android.bignerdranch.geoquiz
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private var numQuestionsAnswered: Double = 0.0
    private var numCorrectAnswers: Double = 0.0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle? called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.isClickable = true
            falseButton.isClickable = true
        }

        prevButton.setOnClickListener { // Creating a Previous button Challenge
            quizViewModel.moveToPrev()
            updateQuestion()
            trueButton.isClickable = true
            falseButton.isClickable = true
        }

        questionTextView.setOnClickListener { // Clicking on the text view to change question Challenge
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.isClickable = true
            falseButton.isClickable = true
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        trueButton.isClickable = false
        falseButton.isClickable = false
        numQuestionsAnswered += 1
        val correctAnswer = quizViewModel.currentQuestionAnswer
        var messageResId = ""
        if (userAnswer == correctAnswer) {
            messageResId = "Correct!"
            numCorrectAnswers += 1
        } else {
            messageResId = "Incorrect!"
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (numQuestionsAnswered >= quizViewModel.getQuestionBankSize()) {
            if (numQuestionsAnswered >= quizViewModel.getQuestionBankSize()) {
                val percentageOfCorrectAnswers = (numCorrectAnswers / numQuestionsAnswered) * 100
                val messageCorrectPercentage = "$percentageOfCorrectAnswers%"
                Toast.makeText(this, messageCorrectPercentage, Toast.LENGTH_LONG).show()
                numCorrectAnswers = 0.0
                numQuestionsAnswered = 0.0
            }
        }
    }
}