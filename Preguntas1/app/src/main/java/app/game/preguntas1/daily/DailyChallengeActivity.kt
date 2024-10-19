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


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ChallengeData")

class DailyChallengeActivity : AppCompatActivity() {

    companion object {
        const val STREAK_DAYS = "streak_days"
        const val YESTERDAY_KEY = "yesterday"
        const val TODAY_KEY = "today"
        const val TOMORROW_KEY = "tomorrow"
    }

    private lateinit var binding: ActivityDailyChallengeBinding
    private var firstTime: Boolean = true
    private var streakNow: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyChallengeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val context = applicationContext
        readChallenge(context, getYesterdayDate(), getTodayDate(), getTomorrowDate())
        CoroutineScope(Dispatchers.IO).launch {
            getStreak().filter { firstTime }.collect { ChallengeData ->
                if (ChallengeData != null) {
                    runOnUiThread {
                        streakNow = ChallengeData.streak
                        binding.cbYesterday.isChecked = ChallengeData.cbYesterday
                        binding.cbToday.isChecked = ChallengeData.cbToday
                        binding.cbTomorrow.isChecked = ChallengeData.cbTomorrow
                        firstTime = !firstTime
                    }
                }
            }
        }
        initUI()
        binding.streakPoints.text = streakNow.toString()
        binding.cbYesterday.setOnCheckedChangeListener{ _, value ->
            if(value){
                streakNow++
            }else{
                streakNow--
            }
            binding.streakPoints.text = streakNow.toString()
        }

        binding.cbToday.setOnCheckedChangeListener{ _, value ->
            if(value){
                streakNow++
            }else{
                streakNow--
            }
            binding.streakPoints.text = streakNow.toString()
        }

    }

    private fun readChallenge(context: Context, yesterday: String, today: String, tomorrow: String){
        val yesterdayDate = context.resources.getIdentifier("$yesterday", "string", context.packageName)
        val todayDate = context.resources.getIdentifier("$today", "string", context.packageName)
        val tomorrowDate = context.resources.getIdentifier("$tomorrow", "string", context.packageName)
        binding.tvToday.text = todayDate.toString()
        binding.tvYesterday.text = yesterdayDate.toString()
        binding.tvTomorrow.text = tomorrowDate.toString()
    }

    private fun initUI() {

        binding.cbYesterday.setOnCheckedChangeListener{ _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveCheckboxs(YESTERDAY_KEY, value)
                saveStreak(streakNow)
            }
        }
        binding.cbToday.setOnCheckedChangeListener{ _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveCheckboxs(TODAY_KEY, value)
                saveStreak(streakNow)
            }
        }
        binding.cbTomorrow.setOnCheckedChangeListener{ _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveCheckboxs(TOMORROW_KEY, value)
                saveStreak(streakNow)
            }
        }
    }

    private suspend fun saveStreak(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(STREAK_DAYS)] = value
        }
    }

    private suspend fun saveCheckboxs(key: String, value: Boolean){
        dataStore.edit{preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getStreak(): Flow<ChallengeData?> {
        return dataStore.data.map { preferences ->
            ChallengeData(
                streak = preferences[intPreferencesKey(STREAK_DAYS)] ?: 0,
                cbYesterday = preferences[booleanPreferencesKey(YESTERDAY_KEY)] ?: false,
                cbToday = preferences[booleanPreferencesKey(TODAY_KEY)] ?: false,
                cbTomorrow = preferences[booleanPreferencesKey(TOMORROW_KEY)] ?: false
            )
        }
    }

    fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val TodayDate = Date()
        return dateFormat.format(TodayDate)
    }

    fun getTomorrowDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrowDate: Date = calendar.time
        return dateFormat.format(tomorrowDate)
    }

    fun getYesterdayDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayDate: Date = calendar.time
        return dateFormat.format(yesterdayDate)
    }

}

