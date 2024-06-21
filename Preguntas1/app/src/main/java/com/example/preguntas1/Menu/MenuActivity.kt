package com.example.preguntas1.Menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.preguntas1.Preguntas.PreguntasActivity
import com.example.preguntas1.R

class MenuActivity : AppCompatActivity() {

    companion object {
        const val TYPE_KEY = "ACTUAL_TYPE"
        const val WHO_KEY: String = "WHO_IS"
        const val DEEP_KEY: String = "DEEP_QUEST"
        const val MET_KEY: String = "MET_QUEST"
        const val RANDOM_KEY: String = "RANDOM"
        const val LINES_KEY: String = "LINES"
    }

    private lateinit var btnOne: CardView
    private lateinit var btnTwo: CardView
    private lateinit var btnThree: CardView
    private lateinit var btnFive: CardView
    private lateinit var btnSix: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu2)
        btnOne = findViewById(R.id.btnOne)
        btnTwo = findViewById(R.id.btnTwo)
        btnThree = findViewById(R.id.btnThree)
        btnFive = findViewById(R.id.btnFive)
        btnSix = findViewById(R.id.btnSix)

        btnOne.setOnClickListener { navigateToQuestions(WHO_KEY) }
        btnTwo.setOnClickListener { navigateToQuestions(DEEP_KEY) }
        btnThree.setOnClickListener { navigateToQuestions(MET_KEY) }
        btnFive.setOnClickListener  { navigateToQuestions(RANDOM_KEY)}
        btnSix.setOnClickListener  { navigateToQuestions(LINES_KEY)}
    }

    private fun navigateToQuestions(type: String) {
        val intent = Intent(this, PreguntasActivity::class.java)
        intent.putExtra(TYPE_KEY, type)
        startActivity(intent)
    }


}

