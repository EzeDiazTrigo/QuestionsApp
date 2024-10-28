package app.game.preguntas1.daily

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.game.preguntas1.Menu.MenuActivity.Companion.THEME_KEY
import app.game.preguntas1.Menu.SettingsData
import app.game.preguntas1.Menu.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.game.preguntas1.Menu.MenuActivity.Companion.themeDark
import app.game.preguntas1.R
import app.game.preguntas1.databinding.ActivityDailyChallengeBinding
import app.game.preguntas1.databinding.ActivityMenu2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.PreferencesProto.PreferenceMapOrBuilder
import androidx.datastore.preferences.core.stringPreferencesKey
import app.game.preguntas1.Menu.MenuActivity.Companion.CURRENT_KEY
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.withContext


val Context.challengeDataStore: DataStore<Preferences> by preferencesDataStore(name = "ChallengeData")
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class DailyChallengeActivity : AppCompatActivity() {

    companion object {
        const val STREAK_DAYS = "streak_days"
        const val YESTERDAY_KEY = "yesterday"
        const val TODAY_KEY = "today"
        const val CHALLENGE_HISTORY_KEY = "challenge_history"
        const val LAST_USED_DATE = "last_used_date"
    }

    private lateinit var binding: ActivityDailyChallengeBinding
    private var streakNow: Int = 0
    private var initializingCheckboxes: Boolean = true
    private var historialChallenges: MutableMap<String, Boolean> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyChallengeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val context = applicationContext
        readChallenge(context, getAnyDate(-1), getTodayDate(), getAnyDate(1))
        CoroutineScope(Dispatchers.IO).launch {
            getStreak().filter { initializingCheckboxes }.collect { ChallengeData ->
                if (ChallengeData != null) {
                    runOnUiThread {
                        streakNow = ChallengeData.streak
                        binding.streakPoints.text = streakNow.toString()
                        upDateStreakColor()
                        hideSystemUI()
                        historialChallenges = ChallengeData.challengeHistory
                        binding.cbYesterday.isChecked = historialChallenges[getAnyDate(-1)] == true
                        binding.cbToday.isChecked = historialChallenges[getTodayDate()] == true
                        initializingCheckboxes = !initializingCheckboxes
                    }
                }
            }
        }

        //Resetear la racha - borrar antes de mergear
        /*CoroutineScope(Dispatchers.IO).launch {
            resetStreak()
        }*/

        initUI(historialChallenges)
        initButtons()
    }

    private fun getSettings(): Flow<SettingsData?> {
        return dataStore.data.map { preferences ->
            SettingsData(
                theme = preferences[booleanPreferencesKey(THEME_KEY)] ?: false,
                currentDate = preferences[stringPreferencesKey(CURRENT_KEY)] ?:""
            )
        }
    }

    private fun initButtons() {
        binding.tvChallengeYesterday.setOnClickListener{
            binding.cbYesterday.isChecked = !binding.cbYesterday.isChecked
        }
        binding.tvChallegeToday.setOnClickListener{
            binding.cbToday.isChecked = !binding.cbToday.isChecked
        }
    }

    private suspend fun resetStreak() {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(STREAK_DAYS)] = 0
        }
    }

    private fun upDateStreakColor() {
        if (streakNow > 1) {
            binding.cvStreakPoints.setCardBackgroundColor(getColor(R.color.background_fireStreak))
            binding.streakPoints.setTextColor(getColor(R.color.text_fireStreak))
            binding.imgFire.setColorFilter(
                ContextCompat.getColor(this, R.color.text_fireStreak),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        } else {
            binding.cvStreakPoints.setCardBackgroundColor(getColor(R.color.background_ZeroStreak))
            binding.streakPoints.setTextColor(getColor(R.color.text_ZeroStreak))
            binding.imgFire.setColorFilter(
                ContextCompat.getColor(this, R.color.text_ZeroStreak),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun readChallenge(
        context: Context,
        yesterday: String,
        today: String,
        tomorrow: String
    ) {
        val yesterdayResId =
            context.resources.getIdentifier("d$yesterday", "string", context.packageName)
        val todayResId = context.resources.getIdentifier("d$today", "string", context.packageName)
        val tomorrowResId =
            context.resources.getIdentifier("d$tomorrow", "string", context.packageName)

        if (yesterdayResId != 0) {
            binding.tvChallengeYesterday.text = context.getString(yesterdayResId)
        } else {
            binding.tvChallengeYesterday.text = "N/A"
        }
        if (todayResId != 0) {
            binding.tvChallegeToday.text = context.getString(todayResId)
        } else {
            binding.tvChallegeToday.text = "N/A"
        }
        if (tomorrowResId != 0) {
            binding.tvChallengeTomorrow.text = context.getString(tomorrowResId)
        } else {
            binding.tvChallengeTomorrow.text = "N/A"
        }
    }

    private fun initUI(historialChallenges: MutableMap<String, Boolean>) {
        binding.streakPoints.text = streakNow.toString()
        binding.cbYesterday.setOnCheckedChangeListener { _, value ->
            if (!initializingCheckboxes) {
                if (value) {
                    streakNow++
                } else {
                    streakNow = maxOf(0, streakNow - 1)
                }
            }
            historialChallenges.put(getTodayDate(), value)
            CoroutineScope(Dispatchers.IO).launch {
                saveChallengeHistory(historialChallenges)
                saveStreak(streakNow)
            }
            binding.streakPoints.text = streakNow.toString()
            upDateStreakColor()
        }

        binding.cbToday.setOnCheckedChangeListener { _, value ->
            if (!initializingCheckboxes) {
                if (value) {
                    streakNow++
                } else {
                    streakNow = maxOf(0, streakNow - 1)
                }
            }
            historialChallenges.put(getTodayDate(), value)
            CoroutineScope(Dispatchers.IO).launch {
                saveChallengeHistory(historialChallenges)
                saveStreak(streakNow)
            }
            binding.streakPoints.text = streakNow.toString()
            upDateStreakColor()
        }
    }

    private fun updateUIForNewDay() {
        binding.cbYesterday.isChecked = binding.cbToday.isChecked
        binding.cbToday.isChecked = false
    }

    private suspend fun saveStreak(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(STREAK_DAYS)] = value
        }
    }

    private suspend fun saveChallengeHistory(challengeHistory: MutableMap<String, Boolean>) {
        val json = Gson().toJson(challengeHistory)
        val challengeHistoryKey = stringPreferencesKey(CHALLENGE_HISTORY_KEY)

        withContext(Dispatchers.IO) {
            challengeDataStore.edit { preferences ->
                preferences[challengeHistoryKey] = json
            }
        }
    }

    private fun getStreak(): Flow<ChallengeData?> {
        return challengeDataStore.data.map { preferences ->
            val streak = preferences[intPreferencesKey(STREAK_DAYS)] ?: 0
            val challengeHistoryJson = preferences[stringPreferencesKey(CHALLENGE_HISTORY_KEY)] ?: "{}"
            val challengeHistory: MutableMap<String, Boolean> = Gson().fromJson(
                challengeHistoryJson, object : TypeToken<MutableMap<String, Boolean>>() {}.type
            )

            ChallengeData(
                streak = streak,
                challengeHistory = challengeHistory
            )
        }
    }

    fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val TodayDate = Date()
        return dateFormat.format(TodayDate)
    }

    fun getAnyDate(move: Int): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, move)
        val tomorrowDate: Date = calendar.time
        return dateFormat.format(tomorrowDate)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }


}




