package com.example.workshopquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

// MainActivity inherits AppCompatActivity to make use of Android compatibility features.
class MainActivity : AppCompatActivity() {

    // Declares variables for the quiz logic and UI components.
    private lateinit var questions: List<Question> // List to hold questions for the quiz.
    private var currentQuestionIndex = 0 // Index to track the current question.
    private var correct = 0 // Counter for the number of correct answers.
    private var score = 0 // Variable to store the final score.

    // UI components for displaying the question and options.
    private lateinit var submitBtn: Button
    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton

    // onCreate is called when the activity is created. Sets up the UI and initializes components.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Sets the UI layout for this activity.

        // Finds and assigns UI elements to variables.
        submitBtn = findViewById(R.id.submitBtn)
        questionText = findViewById(R.id.questionText)
        optionsGroup = findViewById(R.id.optionsGroup)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        // Initializes the list of questions for the quiz.
        questions = listOf(
            Question("What is the capital of France?", listOf("Paris", "London", "Rome", "Berlin"), 0, "Paris is the capital of France."),
            Question("What is the capital of England?", listOf("Paris", "London", "Rome", "Berlin"), 1, "London is the capital of England."),
            // Add more questions here.
        )

        displayQuestion() // Displays the first question.

        // Set a click listener on the submit button to handle answer submission.
        submitBtn.setOnClickListener {
            // Determines the index of the selected radio button within the group.
            val selectedOptionIndex = optionsGroup.indexOfChild(findViewById(optionsGroup.checkedRadioButtonId))
            // Increases the correct answer count if the selected option is correct.
            if (selectedOptionIndex == questions[currentQuestionIndex].correctIndex) {
                correct++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            // Delay the explanation dialog to let the Toast message show
            optionsGroup.postDelayed({
                showExplanation(questions[currentQuestionIndex].explanation)
            }, 1500)

            // Advances to the next question or shows the final score if the quiz is complete.
            if (currentQuestionIndex < questions.size) {
                displayQuestion()
            } else {
                quizFinished(score)
            }
        }
    }

    // Function to update the UI with the current question and its options.
    private fun displayQuestion() {
        val question = questions[currentQuestionIndex]
        questionText.text = "Question ${currentQuestionIndex + 1}/${questions.size}: \n\n${question.text}"
        option1.text = question.options[0]
        option2.text = question.options[1]
        option3.text = question.options[2]
        option4.text = question.options[3]
        optionsGroup.clearCheck() // Clears any previous selection.
    }

    // Function to show an explanation for the current question in a dialog.
    private fun showExplanation(explanation: String) {
        AlertDialog.Builder(this)
            .setTitle("Explanation")
            .setMessage(explanation)
            .setPositiveButton("OK") { dialog, which ->
                currentQuestionIndex++ // Advances to the next question.
                // Re-checks if there are more questions or shows the final score.
                if (currentQuestionIndex < questions.size) {
                    displayQuestion()
                } else {
                    quizFinished(score)
                }
            }
            .setCancelable(false) // Ensures the dialog must be explicitly dismissed.
            .show()
    }

    private fun quizFinished(score: Int) {
        val score = (correct.toFloat() / questions.size) * 100
        Toast.makeText(this, "Quiz Finished! Your score: $score", Toast.LENGTH_LONG).show()
    }
}
