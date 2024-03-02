package com.example.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityGamePadBinding
import com.example.myapplication.databinding.CustomDialogBinding


class GamePad : AppCompatActivity() {

    private lateinit var binding: ActivityGamePadBinding

    private val questions = arrayOf(
        "1. Я действую как представитель этого коллектива.",
        "2. Я предоставляю членам коллектива полную свободу в выполнении работы.",
        "3. Я поощряю применение единообразных способов работы.",
        "4. Я разрешаю подчиненным решать задачи по их усмотрению.",
        "5. Я побуждаю членов коллектива к большему напряжению в работе.",
        "6. Я предоставляю подчиненным возможность делать работу так, как они считают наиболее целесообразным.",
        "7. Я поддерживаю высокий темп работы.",
        "8. Я стараюсь направить помыслы людей на выполнение производственных заданий.",
        "9. Я разрешаю возникающие в коллективе конфликты.",
        "10. Я неохотно предоставляю подчиненным свободу действий.",
        "11. Я решаю сам, что и как должно быть сделано.",
        "12. Я уделяю основное внимание показателям производственной деятельности.",
        "13. Я распределяю поручения подчиненным, исходя из производственной необходимости.",
        "14. Я способствую разным изменениям в производственном коллективе.",
        "15. Я тщательно планирую работу своего коллектива.",
        "16. Я не объясняю подчиненным свои действия и решения.",
        "17. Я стремлюсь убедить подчиненных, что мои действия и намерения – для их пользы.",
        "18. Я предоставляю подчиненным возможность устанавливать свой режим работы.",

        )
    private val options = arrayOf(
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),
        arrayOf("всегда", "часто", "иногда", "редко","никогда"),

        )
    private val correctAnswers = arrayOf(0, 0, 1, 1, 2,4 ,4 ,4,3,3,3,2,1,2,3,2,1,2,3)
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.option1Button.setOnClickListener {
            checkAnswer(0)
        }
        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(3)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(4)
        }
//        binding.restartButton.setOnClickListener { restartQuiz() }
        val buttonHome = findViewById<ImageButton>(R.id.buttonHome)

        buttonHome.setOnClickListener {
            val playIntent = Intent(this, MainActivity::class.java)
            startActivity(playIntent)
        }

    }

//    private fun correctButtonColors(buttonIndex: Int) {
//        when (buttonIndex) {
//            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
//            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
//            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
//        }
//    }
//
//    private fun wrongButtonColors(buttonIndex: Int) {
//        when (buttonIndex) {
//            0 -> binding.option1Button.setBackgroundColor(Color.RED)
//            1 -> binding.option2Button.setBackgroundColor(Color.RED)
//            2 -> binding.option3Button.setBackgroundColor(Color.RED)
//        }
//    }

//    private fun resetButtonColor() {
//        binding.option1Button.setBackgroundColor(Color.rgb(255, 193, 7))
//        binding.option2Button.setBackgroundColor(Color.rgb(255, 193, 7))
//        binding.option3Button.setBackgroundColor(Color.rgb(255, 193, 7))
//    }

    private fun showResults(message: String?) {
        Toast.makeText(this, "Your score: $score out of ${questions.size}", Toast.LENGTH_LONG)
            .show()
//        binding.restartButton.isEnabled = true

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textMassege : TextView = dialog.findViewById(R.id.alert_massege)
        val btnRestart : Button = dialog.findViewById(R.id.restartButton)
        val btnHome : Button = dialog.findViewById(R.id.homeButton)
        textMassege.text = message
        btnHome.setOnClickListener {
            dialog.dismiss()
        }
        btnRestart.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun displayQuestion() {
        binding.questionText.text = questions[currentQuestionIndex]
        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]
        binding.option4Button.text = options[currentQuestionIndex][3]
        binding.option5Button.text = options[currentQuestionIndex][4]
//        resetButtonColor()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]
//        if (selectedAnswerIndex == correctAnswerIndex) {
//            score++
//            correctButtonColors(selectedAnswerIndex)
//        } else {
//            wrongButtonColors(selectedAnswerIndex)
//            correctButtonColors(correctAnswerIndex)
//        }
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.questionText.postDelayed({ displayQuestion() }, 1000)
        } else {
            val message : String? = "Your score: $score out of ${questions.size}"
            showResults(message)
        }
    }
//
//    private fun restartQuiz() {
//        currentQuestionIndex = 0
//        score = 0
//        displayQuestion()
//        binding.restartButton.isEnabled = false
//    }
}