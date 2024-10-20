package app.game.preguntas1.daily

import android.content.Context
import android.os.Bundle
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
import androidx.datastore.preferences.core.stringPreferencesKey


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ChallengeData")

class DailyChallengeActivity : AppCompatActivity() {

    companion object {
        const val STREAK_DAYS = "streak_days"
        const val YESTERDAY_KEY = "yesterday"
        const val TODAY_KEY = "today"
        const val LAST_DATE = "last_date"
        const val LAST_DATE_TEMP = "last_date_temp"
        const val LAST_DATE_TEMP_AUX = "last_date_temp_aux"
    }

    private lateinit var binding: ActivityDailyChallengeBinding
    private var firstTime: Boolean = true
    private var streakNow: Int = 0
    private var lastDate: String = "20241018"
    private var tempLastDate: String = "20241017"
    private var tempLastDateAux: String = "20241016"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyChallengeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val context = applicationContext
        readChallenge(context, getAnyDate(-1), getTodayDate(), getAnyDate(1))
        CoroutineScope(Dispatchers.IO).launch {
            getStreak().filter { firstTime }.collect { ChallengeData ->
                if (ChallengeData != null) {
                    runOnUiThread {
                        streakNow = ChallengeData.streak
                        binding.cbYesterday.isChecked = ChallengeData.cbYesterday
                        binding.cbToday.isChecked = ChallengeData.cbToday
                        lastDate = ChallengeData.lastDate
                        tempLastDate = ChallengeData.tempLastDate
                        tempLastDateAux = ChallengeData.tempLastDateAux
                        firstTime = !firstTime
                    }
                }
            }
        }
        initUI()
        binding.streakPoints.text = streakNow.toString()
        binding.cbYesterday.setOnCheckedChangeListener { _, value ->
            if (value) {
                streakNow++
                CoroutineScope(Dispatchers.IO).launch {
                    saveLastDate(LAST_DATE, getTodayDate())
                    saveLastDate(LAST_DATE_TEMP, lastDate)
                    saveLastDate(LAST_DATE_TEMP_AUX, tempLastDate)
                }

            } else {
                streakNow--
            }
            binding.streakPoints.text = streakNow.toString()
        }

        binding.cbToday.setOnCheckedChangeListener { _, value ->
            if (value) {
                streakNow++

            } else {
                streakNow--
            }
            binding.streakPoints.text = streakNow.toString()
        }

    }

    private fun readChallenge(
        context: Context,
        yesterday: String,
        today: String,
        tomorrow: String
    ) {
        val yesterdayResId =
            context.resources.getIdentifier(yesterday, "string", context.packageName)
        val todayResId = context.resources.getIdentifier(today, "string", context.packageName)
        val tomorrowResId = context.resources.getIdentifier(tomorrow, "string", context.packageName)
        binding.tvYesterday.text =
            if (yesterdayResId != 0) context.getString(yesterdayResId) else "N/A"
        binding.tvToday.text = if (todayResId != 0) context.getString(todayResId) else "N/A"
        binding.tvTomorrow.text =
            if (tomorrowResId != 0) context.getString(tomorrowResId) else "N/A"
    }


    private fun initUI() {
        binding.cbTomorrow.isChecked = false
        binding.cbYesterday.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveCheckboxs(YESTERDAY_KEY, value)
                saveStreak(streakNow)
            }
        }
        binding.cbToday.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveCheckboxs(TODAY_KEY, value)
                saveStreak(streakNow)
            }
        }
        binding.cbToday.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveCheckboxs(TODAY_KEY, value)
                saveStreak(streakNow)
            }
        }
    }

    private suspend fun saveStreak(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(STREAK_DAYS)] = value
        }
    }

    private suspend fun saveCheckboxs(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private suspend fun saveLastDate(key: String, date: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = date
        }
    }

    private fun getStreak(): Flow<ChallengeData?> {
        return dataStore.data.map { preferences ->
            ChallengeData(
                streak = preferences[intPreferencesKey(STREAK_DAYS)] ?: 0,
                cbYesterday = preferences[booleanPreferencesKey(YESTERDAY_KEY)] ?: false,
                cbToday = preferences[booleanPreferencesKey(TODAY_KEY)] ?: false,
                lastDate = preferences[stringPreferencesKey(LAST_DATE)] ?: "",
                tempLastDate = preferences[stringPreferencesKey(LAST_DATE_TEMP)] ?: "",
                tempLastDateAux = preferences[stringPreferencesKey(LAST_DATE_TEMP_AUX)] ?: ""
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
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrowDate: Date = calendar.time
        return dateFormat.format(tomorrowDate)
    }


}




