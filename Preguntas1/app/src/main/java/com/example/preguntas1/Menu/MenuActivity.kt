package com.example.preguntas1.Menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.preguntas1.Preguntas.PreguntasActivity
import com.example.preguntas1.R

class MenuActivity : AppCompatActivity() {

    companion object {
        const val TYPE_KEY = "ACTUAL_TYPE"
        const val WHO_KEY:String = "WHO_IS"
        const val DEEP_KEY:String = "DEEP_QUEST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu2)
        val btnOne = findViewById<AppCompatButton>(R.id.btnOne)
        val btnTwo = findViewById<AppCompatButton>(R.id.btnTwo)

        btnOne.setOnClickListener {navigateToQuestions(WHO_KEY)}
        btnTwo.setOnClickListener{navigateToQuestions(DEEP_KEY)}
    }

    fun navigateToQuestions(type:String){
        val intent = Intent(this, PreguntasActivity::class.java)
        intent.putExtra(TYPE_KEY, type)
        startActivity(intent)
    }


}

