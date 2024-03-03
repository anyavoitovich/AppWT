package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
class MainActivity : AppCompatActivity() {
    private lateinit var levels: JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        // Загрузка уровней тестов из JSON при создании активности
        loadLevelsFromJson()

        // Создание кнопок для каждого уровня тестов
        createLevelButtons()
       }
    // Функция для загрузки уровней тестов из JSON файла
    private fun loadLevelsFromJson() {
        // Прочитать содержимое JSON файла и преобразовать его в JSONObject
        val jsonFileString = getJsonDataFromRaw(applicationContext, R.raw.test)
        val jsonObject = JSONObject(jsonFileString)
        // Получить массив уровней из объекта JSON
        levels = jsonObject.getJSONArray("levels")
    }

    // Функция для создания кнопок для каждого уровня тестов
// Функция для создания кнопок для каждого уровня тестов
// Функция для создания кнопок для каждого уровня тестов
    private fun createLevelButtons() {
        val linearLayout = LinearLayout(this) // Создаем новый LinearLayout
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL // Устанавливаем ориентацию вертикально
        linearLayout.gravity = Gravity.CENTER_VERTICAL // Центрируем элементы по вертикали

        val horizontalMargin = 32.dpToPx() // Отступы справа и слева

        var previousButtonId = 0
        for (i in 0 until levels.length()) {
            val level = levels.getJSONObject(i)
            val levelNumber = level.getInt("level")
            val levelTests = level.getJSONArray("tests")

            // Создаем кнопку для каждого уровня тестов и устанавливаем стиль
            val levelButton = Button(this)
            levelButton.id = View.generateViewId() // Генерируем уникальный идентификатор для кнопки
            levelButton.text = "Level $levelNumber"
            levelButton.setBackgroundResource(R.drawable.rounded_button) // Устанавливаем фон с закругленными углами
            levelButton.setTextColor(resources.getColor(android.R.color.white)) // Устанавливаем цвет текста
            levelButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f) // Устанавливаем размер текста
            levelButton.setPadding(16, 16, 16, 16) // Устанавливаем отступы

            levelButton.setOnClickListener {
                val intent = Intent(this, Levels::class.java)
                intent.putExtra("levelTests", levelTests.toString())
                startActivity(intent)
            }

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 16.dpToPx() // Отступы между кнопками
            layoutParams.leftMargin = horizontalMargin // Отступ слева
            layoutParams.rightMargin = horizontalMargin // Отступ справа
            levelButton.layoutParams = layoutParams

            linearLayout.addView(levelButton) // Добавляем кнопку в LinearLayout
        }

        // Добавляем созданный LinearLayout в родительский ConstraintLayout
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        constraintLayout.addView(linearLayout)
    }

    // Функция для преобразования dp в пиксели
    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }



    // Функция для чтения JSON файла из папки assets
    private fun getJsonDataFromRaw(context: android.content.Context, resId: Int): String? {
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