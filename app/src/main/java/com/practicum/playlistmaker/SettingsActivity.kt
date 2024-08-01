package com.practicum.playlistmaker


import android.content.Intent

import android.net.Uri
import android.os.Bundle

import android.widget.LinearLayout

import androidx.appcompat.widget.Toolbar

import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<Toolbar>(R.id.settings_header)
        val shareAppButton = findViewById<LinearLayout>(R.id.share_app)
        val supportButton = findViewById<LinearLayout>(R.id.email_for_support)
        val termsOfUseButton = findViewById<LinearLayout>(R.id.terms_of_use)

        backButton.setOnClickListener {
            finish()
        }

        shareAppButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/")
            }
            startActivity(shareIntent)
        }

        supportButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:NikolaiBhairava@yandex.ru")
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Сообщение разработчикам и разработчицам приложения Playlist Maker"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Спасибо разработчикам и разработчицам за крутое приложение!"
                )
            }
                startActivity(emailIntent)
        }


        termsOfUseButton.setOnClickListener {
            val termsOfUseIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru/legal/practicum_offer/"))
                startActivity(intent)
        }


    }
}
