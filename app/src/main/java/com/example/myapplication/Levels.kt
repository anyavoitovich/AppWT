package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONArray
import org.json.JSONObject

class Levels : AppCompatActivity() {

    private lateinit var levelTests: JSONArray // Массив тестов для выбранного уровня

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.levels_pad)

        // Получаем тесты выбранного уровня из Intent
        val levelTestsString = intent.getStringExtra("levelTests")
        levelTests = JSONArray(levelTestsString)

        // Создаем кнопки для каждого теста выбранного уровня
        createTestButtons()
    }

    // Функция для создания кнопок для каждого теста выбранного уровня
    private fun createTestButtons() {
        val linearLayout = LinearLayout(this) // Создаем новый LinearLayout
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL // Устанавливаем ориентацию вертикально
        linearLayout.gravity = Gravity.CENTER_VERTICAL // Центрируем элементы по вертикали

        val horizontalMargin = 32.dpToPx() // Отступы слева и справа

        var previousButtonId = 0
        for (i in 0 until levelTests.length()) {
            val test = levelTests.getJSONObject(i)
            val testName = test.getString("test_name")

            // Создаем кнопку для каждого теста и устанавливаем стиль
            val testButton = Button(this)
            testButton.id = View.generateViewId() // Генерируем уникальный идентификатор для кнопки
            testButton.text = testName
            testButton.setBackgroundResource(R.drawable.rounded_button)
            testButton.setTextColor(resources.getColor(android.R.color.white)) // Устанавливаем цвет текста
            testButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f) // Устанавливаем размер текста
            testButton.setPadding(16, 16, 16, 16) // Устанавливаем отступы
            testButton.setOnClickListener {
                val intent = Intent(this, GamePad::class.java)
                // Здесь можно передать дополнительные данные, если это необходимо
                startActivity(intent)
            }

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 16.dpToPx()
            layoutParams.leftMargin = horizontalMargin // Отступ слева
            layoutParams.rightMargin = horizontalMargin // Отступ справа
            testButton.layoutParams = layoutParams

            linearLayout.addView(testButton) // Добавляем кнопку в LinearLayout
        }

        // Добавляем созданный LinearLayout в родительский ConstraintLayout
        val constraintLayout = findViewById<ConstraintLayout>(R.id.levelsLayout)
        constraintLayout.addView(linearLayout)
    }

    // Функция для преобразования dp в пиксели
    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}
