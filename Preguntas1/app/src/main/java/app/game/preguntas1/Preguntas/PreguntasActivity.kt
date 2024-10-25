package app.game.preguntas1.Preguntas

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
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
import app.game.preguntas1.Menu.MenuActivity.Companion.IFYOU_KEY
import app.game.preguntas1.R
import app.game.preguntas1.databinding.ActivityPreguntasBinding
import app.game.preguntas1.databinding.DialogNewQuestionBinding
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

    private lateinit var binding: ActivityPreguntasBinding
    private lateinit var overlayBinding: DialogNewQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overlayBinding = DialogNewQuestionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        hideSystemUI()

        binding.frameMain?.addView(overlayBinding.root)
        overlayBinding.root.visibility = View.GONE

        val context = applicationContext
        val deepQuestionsOriginal: MutableList<String> = mutableListOf()
        val whoIsQuestionsOriginal: MutableList<String> = mutableListOf()
        val metQuestionsOriginal: MutableList<String> = mutableListOf()
        val linesIdeasOriginal: MutableList<String> = mutableListOf()
        val knowQuestionsOriginal: MutableList<String> = mutableListOf()
        val debateQuestonsOriginal: MutableList<String> = mutableListOf()
        val ifYouQuestionsOriginal: MutableList<String> = mutableListOf()
        val type: String = intent.extras?.getString(TYPE_KEY) ?: ""

        when (type) {
            WHO_KEY -> readStringFiles(
                whoIsQuestionsOriginal,
                context,
                getString(R.string.whoisQuestion)
            )

            DEEP_KEY -> readStringFiles(
                deepQuestionsOriginal,
                context,
                getString(R.string.deepQuestion)
            )

            MET_KEY -> readStringFiles(
                metQuestionsOriginal,
                context,
                getString(R.string.metQuestion)
            )

            LINES_KEY -> readStringFiles(
                linesIdeasOriginal,
                context,
                getString(R.string.linesQuestion)
            )

            KNOW_KEY -> readStringFiles(
                knowQuestionsOriginal,
                context,
                getString(R.string.knowQuestion)
            )

            DEBATE_KEY -> readStringFiles(
                debateQuestonsOriginal,
                context,
                getString(R.string.debateQuestion)
            )

            IFYOU_KEY -> readStringFiles(
                ifYouQuestionsOriginal,
                context,
                getString(R.string.ifYouQuestion)
            )

            RANDOM_KEY -> {
                readStringFiles(deepQuestionsOriginal, context, getString(R.string.deepQuestion))
                readStringFiles(whoIsQuestionsOriginal, context, getString(R.string.whoisQuestion))
                readStringFiles(metQuestionsOriginal, context, getString(R.string.metQuestion))
                readStringFiles(linesIdeasOriginal, context, getString(R.string.linesQuestion))
                readStringFiles(knowQuestionsOriginal, context, getString(R.string.knowQuestion))
                readStringFiles(debateQuestonsOriginal, context, getString(R.string.debateQuestion))
                readStringFiles(ifYouQuestionsOriginal, context, getString(R.string.ifYouQuestion))
            }
        }

        val deepQuestions = deepQuestionsOriginal.shuffled()
        var whoIsQuestions: MutableMap<String, Int> = whoIsQuestionsOriginal.associateWith { 0 }.toMutableMap()
        val metQuestions = metQuestionsOriginal.shuffled()
        var linesIdeas: MutableMap<String, Int> = linesIdeasOriginal.associateWith { 0 }.toMutableMap()
        val knowQuestions = knowQuestionsOriginal.shuffled()
        val debateQuestions = debateQuestonsOriginal.shuffled()
        val ifYouQuestions = ifYouQuestionsOriginal.shuffled()
        whoIsQuestions.keys.shuffled()
        linesIdeas.keys.shuffled()

        val randomQuestionsOriginal: MutableList<String> =
            (deepQuestionsOriginal + whoIsQuestionsOriginal + metQuestionsOriginal + linesIdeasOriginal + knowQuestionsOriginal + debateQuestonsOriginal + ifYouQuestionsOriginal).toMutableList()
        val randomQuestions: List<String> = randomQuestionsOriginal.shuffled()

        var actualQuestion: MutableWrapper<Int> = MutableWrapper(0)

        when (type) {
            WHO_KEY -> initQuestionsMap(
                whoIsQuestions,
                ContextCompat.getColor(this, R.color.block_whois),
                getString(R.string.whois)
            )

            DEEP_KEY -> initQuestions(
                deepQuestions,
                ContextCompat.getColor(this, R.color.block_deep),
                getString(R.string.deep)
            )

            MET_KEY -> initQuestions(
                metQuestions,
                ContextCompat.getColor(this, R.color.block_met),
                getString(R.string.met)
            )

            RANDOM_KEY -> initQuestions(
                randomQuestions,
                ContextCompat.getColor(this, R.color.block_random),
                getString(R.string.random)
            )

            LINES_KEY -> initQuestionsMap(
                linesIdeas,
                ContextCompat.getColor(this, R.color.block_15lines),
                getString(R.string.lines)
            )

            KNOW_KEY -> initQuestions(
                knowQuestions,
                ContextCompat.getColor(this, R.color.block_plus),
                getString(R.string.plus)
            )

            DEBATE_KEY -> initQuestions(
                debateQuestions,
                ContextCompat.getColor(this, R.color.block_debate),
                getString(R.string.debate)
            )

            IFYOU_KEY -> initQuestions(
                ifYouQuestions,
                ContextCompat.getColor(this, R.color.block_select),
                getString(R.string.ifyou)
            )
        }

        binding.cvQuestion.setOnClickListener {
            when (type) {
                WHO_KEY -> nextQuestionMap(whoIsQuestions, actualQuestion)
                DEEP_KEY -> nextQuestion(deepQuestions, actualQuestion)
                MET_KEY -> nextQuestion(metQuestions, actualQuestion)
                LINES_KEY -> nextQuestionMap(linesIdeas, actualQuestion)
                KNOW_KEY -> nextQuestion(knowQuestions, actualQuestion)
                DEBATE_KEY -> nextQuestion(debateQuestions, actualQuestion)
                IFYOU_KEY -> nextQuestion(ifYouQuestions, actualQuestion)
                RANDOM_KEY -> nextQuestion(randomQuestions, actualQuestion)
            }
        }

        binding.cvPreQuest.setOnClickListener {
            when (type) {
                WHO_KEY -> preQuestionMap(whoIsQuestions, actualQuestion)
                DEEP_KEY -> preQuestion(deepQuestions, actualQuestion)
                MET_KEY -> preQuestion(metQuestions, actualQuestion)
                LINES_KEY -> preQuestionMap(linesIdeas, actualQuestion)
                KNOW_KEY -> preQuestion(knowQuestions, actualQuestion)
                DEBATE_KEY -> preQuestion(debateQuestions, actualQuestion)
                IFYOU_KEY -> preQuestion(ifYouQuestions, actualQuestion)
                RANDOM_KEY -> preQuestion(randomQuestions, actualQuestion)
            }
        }

        binding.imgQuestion.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.OverlayMain.visibility = View.VISIBLE
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    binding.OverlayMain.visibility = View.GONE
                    true
                }

                else -> false
            }
        }

    }

    private fun preQuestion(
        questionList: List<String>,
        actualQuestion: MutableWrapper<Int>
    ) {
        binding.Question.text = questionList[lessActual(actualQuestion)]
    }

    private fun preQuestionMap(
        questionMap: MutableMap<String, Int>,
        actualQuestion: MutableWrapper<Int>
    ) {
        binding.Question.text  = questionMap.keys.elementAt(lessActual(actualQuestion))
    }

    private fun nextQuestion(
        questionList: List<String>,
        actualQuestion: MutableWrapper<Int>
    ) {
        binding.Question.text = questionList[addActual(actualQuestion, questionList.size)]
    }

    private fun nextQuestionMap(
        questionMap: MutableMap<String, Int>,
        actualQuestion: MutableWrapper<Int>
    ) {
        binding.Question.text  = questionMap.keys.elementAt(lessActual(actualQuestion))
    }

    private fun initQuestions(
        questions: List<String>,
        colorTheme: Int,
        title: String
    ) {
        binding.Question.text = questions[0]
        binding.cvQuestion.setCardBackgroundColor(colorTheme)
        binding.cvQuestionGeneral.setCardBackgroundColor(colorTheme)
        binding.cvPreQuest.setCardBackgroundColor(colorTheme)
        binding.titleQuestion.text = title
        binding.titleQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
    }

    private fun initQuestionsMap(
        questions: MutableMap<String, Int>,
        colorTheme: Int,
        title: String
    ) {
        binding.Question.text = questions.keys.elementAt(0)
        binding.cvQuestion.setCardBackgroundColor(colorTheme)
        binding.cvQuestionGeneral.setCardBackgroundColor(colorTheme)
        binding.cvPreQuest.setCardBackgroundColor(colorTheme)
        binding.titleQuestion.text = title
        binding.titleQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
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

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }
}