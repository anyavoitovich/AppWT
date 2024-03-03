package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.myapplication.databinding.ActivityGamePadBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView

class GamePad : AppCompatActivity() {

    private lateinit var binding: ActivityGamePadBinding
    private lateinit var tests: JSONArray // Массив тестов из JSON
    private lateinit var questions: JSONArray // Массив вопросов текущего теста
    private var currentTestIndex = 0 // Индекс текущего теста
    private var currentQuestionIndex = 0 // Индекс текущего вопроса
    private var score = 0 // Очки игрока

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Загрузка тестов из JSON при создании активности
        loadTestsFromJson()

        // Выбор первого теста и отображение первого вопроса
        loadTestAndDisplayQuestion()

        // Назначение обработчиков для кнопок ответов
        binding.option1Button.setOnClickListener { checkAnswer(0) }
        binding.option2Button.setOnClickListener { checkAnswer(1) }
        binding.option3Button.setOnClickListener { checkAnswer(2) }
        binding.option4Button.setOnClickListener { checkAnswer(3) }
        binding.option5Button.setOnClickListener { checkAnswer(4) }

        // Обработчик кнопки для возвращения на главный экран
        val buttonHome = findViewById<ImageButton>(R.id.buttonHome)
        buttonHome.setOnClickListener {
            val playIntent = Intent(this, MainActivity::class.java)
            startActivity(playIntent)
        }
    }

    // Функция для загрузки тестов из JSON файла
    private fun loadTestsFromJson() {
        // Прочитать содержимое JSON файла и преобразовать его в JSONObject
        val jsonFileString = getJsonDataFromRaw(applicationContext, R.raw.test)
        val jsonObject = JSONObject(jsonFileString)
        // Получить массив уровней из объекта JSON
        val levelsArray = jsonObject.getJSONArray("levels")
        // Получить текущий уровень (пока что только первый)
        val currentLevel = levelsArray.getJSONObject(0)
        // Получить массив тестов из текущего уровня
        tests = currentLevel.getJSONArray("tests")
    }

    // Функция для загрузки текущего теста и отображения первого вопроса
    private fun loadTestAndDisplayQuestion() {
        // Получаем текущий объект теста из массива тестов
        val test = tests.getJSONObject(currentTestIndex)
        // Получаем название текущего теста
        val testName = test.getString("test_name")
        // Устанавливаем название теста на экране
//        binding.testNameText.text = testName
        // Получаем массив вопросов текущего теста
        questions = test.getJSONArray("questions")
        // Отображаем первый вопрос
        displayQuestion()
    }

    // Функция для отображения текущего вопроса
    private fun displayQuestion() {
        // Получаем текущий объект вопроса из массива вопросов
        val questionObj = questions.getJSONObject(currentQuestionIndex)
        // Устанавливаем текст вопроса на экране
        binding.questionText.text = questionObj.getString("question")
        // Получаем массив вариантов ответов и их баллы
        val options = questionObj.getJSONArray("options")
        // Устанавливаем тексты вариантов ответов на кнопки
        binding.option1Button.text = options.getString(0)
        binding.option2Button.text = options.getString(1)
        binding.option3Button.text = options.getString(2)
        binding.option4Button.text = options.getString(3)
        binding.option5Button.text = options.getString(4)
    }

    // Функция для проверки выбранного ответа
// Функция для проверки выбранного ответа
    private fun checkAnswer(selectedAnswerIndex: Int) {
        // Получаем текущий объект вопроса из массива вопросов
        val questionObj = questions.getJSONObject(currentQuestionIndex)
        // Получаем массив баллов за варианты ответов
        val scores = questionObj.getJSONArray("scores")
        // Получаем количество баллов за выбранный ответ
        val scoreForSelectedAnswer = scores.getInt(selectedAnswerIndex)
        // Увеличиваем общий счет на количество баллов за выбранный ответ
        score += scoreForSelectedAnswer
        // Переходим к следующему вопросу, если есть еще вопросы
        if (currentQuestionIndex < questions.length() - 1) {
            currentQuestionIndex++
            displayQuestion()
        } else {
            // Если вопросы в текущем тесте закончились
            // Отображаем результаты текущего теста
            showResultsDialog(score)
        }
    }

    // Функция для отображения результатов теста
    @SuppressLint("MissingInflatedId")
    private fun showResultsDialog(score: Int) {
        // Создаем диалоговое окно
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val dialog = Dialog(this)
        dialog.setContentView(dialogView)

        // Находим текстовое поле для отображения сообщения о результатах
        val resultTextView = dialogView.findViewById<TextView>(R.id.alert_message)
        resultTextView.text = "Number of correct answers: $score"

        // Находим кнопку для повторного прохождения теста
        val restartButton = dialogView.findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            // Сбрасываем счет и переходим к первому тесту и первому вопросу
            this.score = 0
            currentTestIndex = 0
            currentQuestionIndex = 0
            loadTestAndDisplayQuestion()
            dialog.dismiss() // Закрываем диалоговое окно
        }

        // Находим кнопку для перехода к списку тестов
        val testsListButton = dialogView.findViewById<Button>(R.id.testsListButton)
        testsListButton.setOnClickListener {
            val intent = Intent(this, Levels::class.java)
            startActivity(intent) // Переходим к списку тестов
            dialog.dismiss() // Закрываем диалоговое окно
        }

        // Находим кнопку для перехода на главный экран
        val homeButton = dialogView.findViewById<Button>(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Переходим на главный экран
            dialog.dismiss() // Закрываем диалоговое окно
        }

        dialog.show() // Отображаем диалоговое окно
    }



    // Функция для чтения JSON файла из папки assets
    private fun getJsonDataFromRaw(context: Context, resId: Int): String? {
        return try {
            val inputStream = context.resources.openRawResource(resId)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}
