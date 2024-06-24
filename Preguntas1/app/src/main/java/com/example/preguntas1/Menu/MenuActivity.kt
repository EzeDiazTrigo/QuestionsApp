package com.example.preguntas1.Menu

import android.app.Dialog
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
        const val KNOW_KEY: String = "KNOW_ME"
    }

    private lateinit var btnWhois: CardView
    private lateinit var btnDeep: CardView
    private lateinit var btnMet: CardView
    private lateinit var btnKnow: CardView
    private lateinit var btnChoose: CardView
    private lateinit var btnRandom: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu2)
        initialization()
        initUI()
    }

    /*private fun showDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_new_question)
        dialog.show()
    }*/

    private fun initialization(){
        btnWhois = findViewById(R.id.btnWhois)
        btnDeep = findViewById(R.id.btnDeep)
        btnMet = findViewById(R.id.btnMet)
        btnKnow = findViewById(R.id.btnKnow)
        btnChoose = findViewById(R.id.btnChoose)
        btnRandom = findViewById(R.id.btnRandom)

    }

    private fun initUI(){
        btnWhois.setOnClickListener { navigateToQuestions(WHO_KEY) }
        btnDeep.setOnClickListener { navigateToQuestions(DEEP_KEY) }
        btnMet.setOnClickListener { navigateToQuestions(MET_KEY) }
        btnKnow.setOnClickListener { navigateToQuestions(KNOW_KEY) }
        btnRandom.setOnClickListener  { navigateToQuestions(RANDOM_KEY) }
        btnChoose.setOnClickListener  { navigateToQuestions(LINES_KEY) }

    }

    private fun navigateToQuestions(type: String) {
        val intent = Intent(this, PreguntasActivity::class.java)
        intent.putExtra(TYPE_KEY, type)
        startActivity(intent)
    }


}

