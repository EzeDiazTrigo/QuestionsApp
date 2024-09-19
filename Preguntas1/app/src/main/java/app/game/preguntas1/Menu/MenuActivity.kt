package app.game.preguntas1.Menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import app.game.preguntas1.Preguntas.PreguntasActivity
import app.game.preguntas1.R

class MenuActivity : AppCompatActivity() {

    companion object {
        const val TYPE_KEY = "ACTUAL_TYPE"
        const val WHO_KEY: String = "WHO_IS"
        const val DEEP_KEY: String = "DEEP_QUEST"
        const val MET_KEY: String = "MET_QUEST"
        const val RANDOM_KEY: String = "RANDOM"
        const val LINES_KEY: String = "LINES"
        const val KNOW_KEY: String = "KNOW_ME"
        const val DEBATE_KEY: String = "DEBATE"
    }

    private lateinit var btnWhois: CardView
    private lateinit var btnDeep: CardView
    private lateinit var btnMet: CardView
    private lateinit var btnKnow: CardView
    private lateinit var btnChoose: CardView
    private lateinit var btnRandom: CardView
    private lateinit var btnDebate: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu2)
        initialization()
        initUI()
    }

    private fun initialization(){
        btnWhois = findViewById(R.id.btnWhois)
        btnDeep = findViewById(R.id.btnDeep)
        btnMet = findViewById(R.id.btnMet)
        btnKnow = findViewById(R.id.btnKnow)
        btnChoose = findViewById(R.id.btnChoose)
        btnRandom = findViewById(R.id.btnRandom)
        btnDebate = findViewById(R.id.btnDebate)
    }

    private fun initUI(){
        btnWhois.setOnClickListener { navigateToQuestions(WHO_KEY) }
        btnDeep.setOnClickListener { navigateToQuestions(DEEP_KEY) }
        btnMet.setOnClickListener { navigateToQuestions(MET_KEY) }
        btnKnow.setOnClickListener { navigateToQuestions(KNOW_KEY) }
        btnRandom.setOnClickListener  { navigateToQuestions(RANDOM_KEY) }
        btnChoose.setOnClickListener  { navigateToQuestions(LINES_KEY) }
        btnDebate.setOnClickListener { navigateToQuestions(DEBATE_KEY) }
    }

    private fun navigateToQuestions(type: String) {
        val intent = Intent(this, PreguntasActivity::class.java)
        intent.putExtra(TYPE_KEY, type)
        startActivity(intent)
    }


}

