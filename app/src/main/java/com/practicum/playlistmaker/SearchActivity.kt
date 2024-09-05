package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.adapter.TrackAdapter
import com.practicum.playlistmaker.apiservice.RequestBuilder
import com.practicum.playlistmaker.dto.SearchResultDTO
import com.practicum.playlistmaker.dto.SongDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBox: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TrackAdapter
    private val tracks = mutableListOf<SongDTO>()
    private var searchText: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backButton = findViewById<Toolbar>(R.id.search_header)
        searchBox = findViewById(R.id.search_box)
        clearButton = findViewById(R.id.clear_button)

        backButton.setOnClickListener {
            finish()
        }

        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                search(searchText)
                clearButton.visibility = if (s.isNullOrEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            searchBox.text.clear()
            hideKeyboard()
        }

        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, "")
            searchBox.setText(searchText)
        }


        recycler = findViewById<RecyclerView>(R.id.trackList)
        adapter = TrackAdapter(tracks)


        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT_KEY, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, "")
        searchBox.setText(searchText)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchBox.windowToken, 0)
    }

    private fun search(text: String) {
        RequestBuilder.api.search("song", text).enqueue(object : Callback<SearchResultDTO> {
            override fun onResponse(p0: Call<SearchResultDTO>, p1: Response<SearchResultDTO>) {
                if (p1.isSuccessful) {
                    val response = p1.body()?.results ?: emptyList()
                    adapter.updateItems(response)
                } else {
                    Toast.makeText(this@SearchActivity,
                        "Ошибка получения списка треков: " + p1.body(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<SearchResultDTO>, p1: Throwable) {
                Toast.makeText(this@SearchActivity,
                    "Ошибка получения списка треков", Toast.LENGTH_SHORT).show()
                Log.e("request", p1.stackTraceToString())
            }

        })

    }


    companion object {
        private const val SEARCH_TEXT_KEY = "SEARCH_TEXT"
    }
}
