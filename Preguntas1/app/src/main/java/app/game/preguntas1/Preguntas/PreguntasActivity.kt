package app.game.preguntas1.Preguntas

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView

import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import app.game.preguntas1.Menu.MenuActivity.Companion.DEEP_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.MET_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.TYPE_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.WHO_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.RANDOM_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.LINES_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.KNOW_KEY
import app.game.preguntas1.Menu.MenuActivity.Companion.DEBATE_KEY
import app.game.preguntas1.R
import kotlin.random.Random

data class MutableWrapper<Int>(var value: Int)

fun addActual(wrapper: MutableWrapper<Int>, limit: Int): Int {
    if (wrapper.value < (limit - 1)) {
        wrapper.value += 1
    }
    return wrapper.value
}

fun lessActual(wrapper: MutableWrapper<Int>): Int {
    if (wrapper.value > 0) {
        wrapper.value -= 1
    }
    return wrapper.value
}

class PreguntasActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var cvQuestion: CardView
    private lateinit var tvtitleQuestion: TextView
    private lateinit var cvPreQuest: CardView
    private lateinit var cvQuestionGeneral: CardView
    private lateinit var imgQuestion: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preguntas)

        val context = applicationContext
        val deepQuestionsOriginal: MutableList<String> = mutableListOf()
        val whoIsQuestionsOriginal: MutableList<String> = mutableListOf()
        val metQuestionsOriginal: MutableList<String> = mutableListOf()
        val linesIdeasOriginal: MutableList<String> = mutableListOf()
        val knowQuestionsOriginal: MutableList<String> = mutableListOf()
        val debateQuestonsOriginal: MutableList<String> = mutableListOf()
        chargeLists(
            context,
            deepQuestionsOriginal,
            whoIsQuestionsOriginal,
            metQuestionsOriginal,
            linesIdeasOriginal,
            knowQuestionsOriginal,
            debateQuestonsOriginal
        )

        val deepQuestions = deepQuestionsOriginal.shuffled()
        val whoIsQuestions = whoIsQuestionsOriginal.shuffled()
        val metQuestions = metQuestionsOriginal.shuffled()
        val linesIdeas = linesIdeasOriginal.shuffled()
        val knowQuestions = knowQuestionsOriginal.shuffled()
        val debateQuestions = debateQuestonsOriginal.shuffled()

        val randomQuestionsOriginal:MutableList<String> = (deepQuestions + whoIsQuestions + metQuestions + linesIdeas + knowQuestions + debateQuestions).toMutableList()
        val randomQuestions:List<String> = randomQuestionsOriginal.shuffled()

        val type: String = intent.extras?.getString(TYPE_KEY) ?: ""

        asingIDs()

        var actualQuestion: MutableWrapper<Int> = MutableWrapper(0)

        when (type) {
            WHO_KEY -> initQuestions(
                whoIsQuestions,
                ContextCompat.getColor(this, R.color.textlight),
                ContextCompat.getColor(this, R.color.block_whois),
                getString(R.string.whois)
            )

            DEEP_KEY -> initQuestions(
                deepQuestions,
                ContextCompat.getColor(this, R.color.textdark),
                ContextCompat.getColor(this, R.color.block_deep),
                getString(R.string.deep)
            )

            MET_KEY -> initQuestions(
                metQuestions,
                ContextCompat.getColor(this, R.color.textlight),
                ContextCompat.getColor(this, R.color.block_met),
                getString(R.string.met)
            )


            RANDOM_KEY -> initQuestions(
                randomQuestions,
                ContextCompat.getColor(this, R.color.textdark),
                ContextCompat.getColor(this, R.color.block_random),
                getString(R.string.random)
            )

            LINES_KEY -> initQuestions(
                linesIdeas,
                ContextCompat.getColor(this, R.color.textlight),
                ContextCompat.getColor(this, R.color.block_15lines),
                getString(R.string.lines)
            )

            KNOW_KEY -> initQuestions(
                knowQuestions,
                ContextCompat.getColor(this, R.color.textdark),
                ContextCompat.getColor(this, R.color.block_plus),
                getString(R.string.plus)
            )

            DEBATE_KEY -> initQuestions(
                debateQuestions,
                ContextCompat.getColor(this, R.color.textdark),
                ContextCompat.getColor(this, R.color.block_debate),
                getString(R.string.debate)
            )
        }
        cvQuestion.setOnClickListener {
            nextQuestion(
                type,
                whoIsQuestions,
                deepQuestions,
                metQuestions,
                linesIdeas,
                knowQuestions,
                actualQuestion,
                debateQuestions,
                randomQuestions
            )
        }

        cvPreQuest.setOnClickListener {
            preQuestion(
                type,
                whoIsQuestions,
                deepQuestions,
                metQuestions,
                linesIdeas,
                knowQuestions,
                actualQuestion,
                debateQuestions,
                randomQuestions
            )
        }

        imgQuestion.setOnClickListener {
            showDialog()
        }
    }

    private fun asingIDs() {
        tvQuestion = findViewById(R.id.Question)
        cvQuestion = findViewById(R.id.cvQuestion)
        tvtitleQuestion = findViewById(R.id.titleQuestion)
        cvQuestionGeneral = findViewById(R.id.cvQuestionGeneral)
        cvPreQuest = findViewById(R.id.cvPreQuest)
        imgQuestion = findViewById(R.id.imgQuestion)
    }

    private fun showDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_new_question)
        dialog.show()
    }

    private fun chargeLists(
        context: Context,
        deepQuestionsOriginal: MutableList<String>,
        whoIsQuestionsOriginal: MutableList<String>,
        metQuestionsOriginal: MutableList<String>,
        linesIdeasOriginal: MutableList<String>,
        knowQuestionsOriginal: MutableList<String>,
        debateQuestionsOriginal: MutableList<String>
    ) {
        readStringFiles(deepQuestionsOriginal, context, getString(R.string.deepQuestion))
        readStringFiles(whoIsQuestionsOriginal, context, getString(R.string.whoisQuestion))
        readStringFiles(metQuestionsOriginal, context, getString(R.string.metQuestion))
        readStringFiles(linesIdeasOriginal, context, getString(R.string.linesQuestion))
        readStringFiles(knowQuestionsOriginal, context, getString(R.string.knowQuestion))
        readStringFiles(debateQuestionsOriginal, context, getString(R.string.debateQuestion))
    }

    private fun preQuestion(
        type: String,
        whoIsQuestions: List<String>,
        deepQuestions: List<String>,
        metQuestions: List<String>,
        linesIdeas: List<String>,
        knowQuestions: List<String>,
        actualQuestion: MutableWrapper<Int>,
        debateQuestions: List<String>,
        randomQuestions: List<String>
    ) {
        when (type) {
            WHO_KEY -> tvQuestion.text = whoIsQuestions[lessActual(actualQuestion)]
            DEEP_KEY -> tvQuestion.text = deepQuestions[lessActual(actualQuestion)]
            MET_KEY -> tvQuestion.text = metQuestions[lessActual(actualQuestion)]
            RANDOM_KEY -> tvQuestion.text = randomQuestions[lessActual(actualQuestion)]
            LINES_KEY -> tvQuestion.text = linesIdeas[lessActual(actualQuestion)]
            KNOW_KEY -> tvQuestion.text = knowQuestions[lessActual(actualQuestion)]
            DEBATE_KEY -> tvQuestion.text = debateQuestions[lessActual(actualQuestion)]
        }
    }

    private fun nextQuestion(
        type: String,
        whoIsQuestions: List<String>,
        deepQuestions: List<String>,
        metQuestions: List<String>,
        linesIdeas: List<String>,
        knowQuestions: List<String>,
        actualQuestion: MutableWrapper<Int>,
        debateQuestions: List<String>,
        randomQuestions: List<String>
    ) {
        when (type) {
            WHO_KEY -> tvQuestion.text = whoIsQuestions[addActual(actualQuestion, whoIsQuestions.size)]
            DEEP_KEY -> tvQuestion.text = deepQuestions[addActual(actualQuestion, deepQuestions.size)]
            MET_KEY -> tvQuestion.text = metQuestions[addActual(actualQuestion, metQuestions.size)]
            RANDOM_KEY -> tvQuestion.text = randomQuestions[addActual(actualQuestion, randomQuestions.size)]
            LINES_KEY -> tvQuestion.text = linesIdeas[addActual(actualQuestion, linesIdeas.size)]
            KNOW_KEY -> tvQuestion.text = knowQuestions[addActual(actualQuestion, knowQuestions.size)]
            DEBATE_KEY -> tvQuestion.text = debateQuestions[addActual(actualQuestion, debateQuestions.size)]
        }
    }

    private fun initQuestions(
        questions: List<String>,
        colorText: Int,
        colorTheme: Int,
        title: String
    ) {
        tvQuestion.text = questions[0]
        tvQuestion.setTextColor(colorText)
        cvQuestion.setCardBackgroundColor(colorTheme)
        cvQuestionGeneral.setCardBackgroundColor(colorTheme)
        cvPreQuest.setCardBackgroundColor(colorTheme)
        tvtitleQuestion.text = title
        tvtitleQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
    }

    private fun readStringFiles(
        actualList: MutableList<String>,
        context: Context,
        typeText: String
    ) {

        var i = 1
        var resourseId: Int
        do {
            resourseId =
                context.resources.getIdentifier("$typeText$i", "string", context.packageName)
            if (resourseId != 0) {
                val stringFromResource = context.getString(resourseId)
                actualList.add(stringFromResource)
                i++
            }
        } while (resourseId != 0)
    }

}