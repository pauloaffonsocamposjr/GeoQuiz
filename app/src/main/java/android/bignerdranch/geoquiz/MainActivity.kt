package android.bignerdranch.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : ImageButton
    private lateinit var prevButton : ImageButton
    private lateinit var questionTextView : TextView
    private var numQuestionsAnswered : Double = 0.0
    private var numCorrectAnswers : Double = 0.0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle? called")
        setContentView(R.layout.activity_main)

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
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            trueButton.isClickable = true
            falseButton.isClickable = true
        }

        prevButton.setOnClickListener { // Creating a Previous button challenge
            if (currentIndex > 0) {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }
        }

        questionTextView.setOnClickListener { // Clicking on the text view to change question Challenge
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
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
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        trueButton.isClickable = false
        falseButton.isClickable = false
        numQuestionsAnswered += 1
        val correctAnswer = questionBank[currentIndex].answer
        var messageResId = ""
        if (userAnswer == correctAnswer) {
            messageResId = "Correct!"
            numCorrectAnswers += 1
        }
        else {
            messageResId = "Incorrect!"
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (numQuestionsAnswered >= questionBank.size) {
            val percentageOfCorrectAnswers = (numCorrectAnswers / numQuestionsAnswered) * 100
            val messageCorrectPercentage = "$percentageOfCorrectAnswers%"
            Toast.makeText(this, messageCorrectPercentage, Toast.LENGTH_LONG).show()
            numCorrectAnswers = 0.0
            numQuestionsAnswered = 0.0
        }
    }
}