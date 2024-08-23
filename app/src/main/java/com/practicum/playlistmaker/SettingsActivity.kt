package com.practicum.playlistmaker

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var themeSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<Toolbar>(R.id.settings_header)
        val shareAppButton = findViewById<LinearLayout>(R.id.share_app)
        val supportButton = findViewById<LinearLayout>(R.id.email_for_support)
        val termsOfUseButton = findViewById<LinearLayout>(R.id.terms_of_use)

        themeSwitch = findViewById(R.id.switch_light_dark_mode)

        sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

        themeSwitch.isChecked = isDarkMode()

        themeSwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            setDarkMode(isChecked)
        }

        backButton.setNavigationOnClickListener {
            finish()
        }

        shareAppButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_url))
            }
            try {
                startActivity(
                    Intent.createChooser(
                        shareIntent,
                        getString(R.string.messenger_success)
                    )
                )
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.no_messenger), Toast.LENGTH_LONG).show()
            }
        }

        supportButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${getString(R.string.email_address)}")
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body))
            }
            try {
                startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.no_email_client), Toast.LENGTH_LONG).show()
            }
        }

        termsOfUseButton.setOnClickListener {
            val termsOfUseIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.terms_of_use_url)))
            try {
                startActivity(termsOfUseIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.no_browser), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setDarkMode(checked: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (checked) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        sharedPreferences.edit().putBoolean("dark_mode", checked).apply()
        recreate()
    }

    private fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean("dark_mode", false)
    }
}

