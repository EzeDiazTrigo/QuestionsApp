package app.game.preguntas1.daily

data class ChallengeData(
    var streak: Int,
    var cbYesterday: Boolean,
    var cbToday: Boolean,
    var lastDate: String,
    var tempLastDate: String,
    var tempLastDateAux: String
)

/*
package app.game.preguntas1.daily

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import app.game.preguntas1.R
import app.game.preguntas1.databinding.ActivityDailyChallengeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ChallengeData")

class DailyChallengeActivity : AppCompatActivity() {

    companion object {
        const val STREAK_DAYS = "streak_days"
        const val LAST_ACTIVITY_DATE = "last_activity_date" // Nueva constante
    }

    private lateinit var binding: ActivityDailyChallengeBinding
    private var streakNow: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyChallengeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            checkAndResetStreakIfNeeded()
            getStreak().collect { challengeData ->
                if (challengeData != null) {
                    runOnUiThread {
                        streakNow = challengeData.streak
                        binding.cbYesterday.isChecked = challengeData.cbYesterday
                        binding.cbToday.isChecked = challengeData.cbToday
                        binding.streakPoints.text = streakNow.toString()
                    }
                }
            }
        }

        initUI()
    }

    private fun initUI() {
        binding.cbYesterday.setOnCheckedChangeListener { _, isChecked ->
            updateStreak(isChecked)
        }

        binding.cbToday.setOnCheckedChangeListener { _, isChecked ->
            updateStreak(isChecked)
        }
    }

    private fun updateStreak(isChecked: Boolean) {
        if (isChecked) streakNow++ else streakNow--
        binding.streakPoints.text = streakNow.toString()

        CoroutineScope(Dispatchers.IO).launch {
            saveStreak(streakNow)
            saveLastActivityDate(getTodayDate())
        }
    }

    private suspend fun checkAndResetStreakIfNeeded() {
        val lastActivityDate = getLastActivityDate().firstOrNull() ?: getTodayDate()
        val todayDate = getTodayDate()

        if (daysBetween(lastActivityDate, todayDate) >= 2) {
            saveStreak(0)  // Resetear el streak a 0
            streakNow = 0
        }
    }

    private suspend fun saveStreak(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(STREAK_DAYS)] = value
        }
    }

    private suspend fun saveLastActivityDate(date: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(LAST_ACTIVITY_DATE)] = date
        }
    }

    private fun getStreak(): Flow<ChallengeData?> {
        return dataStore.data.map { preferences ->
            ChallengeData(
                streak = preferences[intPreferencesKey(STREAK_DAYS)] ?: 0,
                cbYesterday = preferences[booleanPreferencesKey("yesterday")] ?: false,
                cbToday = preferences[booleanPreferencesKey("today")] ?: false
            )
        }
    }

    private fun getLastActivityDate(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(LAST_ACTIVITY_DATE)]
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun daysBetween(startDate: String, endDate: String): Int {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val start = dateFormat.parse(startDate)
        val end = dateFormat.parse(endDate)
        val diff = end.time - start.time
        return (diff / (1000 * 60 * 60 * 24)).toInt()
    }
}

* */