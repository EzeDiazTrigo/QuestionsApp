package app.game.preguntas1.Menu

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import app.game.preguntas1.Preguntas.PreguntasActivity
import app.game.preguntas1.R
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import app.game.preguntas1.databinding.ActivityMenu2Binding
import app.game.preguntas1.databinding.ActivityMenuBinding
import app.game.preguntas1.databinding.ActivityPreguntasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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
        const val IFYOU_KEY: String = "IFYOU"
        const val THEME_KEY: String = "THEME"
        var themeDark: Boolean = false
    }

    private lateinit var binding: ActivityMenu2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().collect { settingsModel ->
                if (settingsModel != null) {
                    runOnUiThread {
                        themeDark = !settingsModel.theme
                        changeTheme(settingsModel.theme)
                    }
                }
            }
        }
        binding = ActivityMenu2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        initUI()

    }

    private fun initUI() {
        binding.btnWhois.setOnClickListener { navigateToQuestions(WHO_KEY) }
        binding.btnDeep.setOnClickListener { navigateToQuestions(DEEP_KEY) }
        binding.btnMet.setOnClickListener { navigateToQuestions(MET_KEY) }
        binding.btnKnow.setOnClickListener { navigateToQuestions(KNOW_KEY) }
        binding.btnRandom.setOnClickListener { navigateToQuestions(RANDOM_KEY) }
        binding.btnChoose.setOnClickListener { navigateToQuestions(LINES_KEY) }
        binding.btnDebate.setOnClickListener { navigateToQuestions(DEBATE_KEY) }
        binding.btnIfYou.setOnClickListener { navigateToQuestions(IFYOU_KEY) }
        binding.imgTheme.setOnClickListener {
            changeTheme(themeDark)
            CoroutineScope(Dispatchers.IO).launch {
                saveTheme(THEME_KEY, themeDark)
            }
        }
    }

    private fun changeTheme(theme: Boolean) {
        val myImageView: ImageView = findViewById(R.id.imgTheme)
        if (theme) {
            myImageView.setImageResource(R.drawable.ic_lightmode)
            enableDarkMode()
        } else {
            myImageView.setImageResource(R.drawable.ic_darkmode)
            disableDarkMode()
        }
    }

    private fun navigateToQuestions(type: String) {
        val intent = Intent(this, PreguntasActivity::class.java)
        intent.putExtra(TYPE_KEY, type)
        startActivity(intent)
    }

    private suspend fun saveTheme(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsData?> {
        return dataStore.data.map { preferences ->
            SettingsData(
                theme = preferences[booleanPreferencesKey(THEME_KEY)] ?: false
            )
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }

}

