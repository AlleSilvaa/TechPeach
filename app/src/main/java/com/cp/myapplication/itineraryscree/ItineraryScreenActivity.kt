package com.cp.myapplication.itineraryscreen

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.cp.myapplication.R
import com.cp.myapplication.model.Itinerario
import com.cp.myapplication.viewmodel.ItineraryViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ItineraryScreenActivity : AppCompatActivity() {

    private lateinit var itineraryTextView: TextView
    private lateinit var loadingIndicator: ContentLoadingProgressBar
    private lateinit var retryButton: Button
    private lateinit var saveButton: Button

    private val viewModel: ItineraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_screen)

        itineraryTextView = findViewById(R.id.itinerary_text)
        loadingIndicator = findViewById(R.id.loading_indicator)
        retryButton = findViewById(R.id.retry_button)
        saveButton = findViewById(R.id.save_button)

        val formData: Itinerario? = intent.getParcelableExtra("formData")

        if (formData != null) {
            generateItinerary(formData)
        } else {
            Toast.makeText(this, "Dados de formulário não encontrados", Toast.LENGTH_SHORT).show()
        }

        retryButton.setOnClickListener {
            if (formData != null) {
                generateItinerary(formData)
            }
        }

        saveButton.setOnClickListener {
            viewModel.itinerary.value?.let { itinerary ->
                saveItinerary(itinerary)
            } ?: Toast.makeText(this, "Nenhum itinerário gerado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateItinerary(formData: Itinerario) {
        loadingIndicator.show()

        lifecycleScope.launch {
            try {
                val reviewsSummary = loadReviewsData()
                val generatedItinerary = simulateApiCall(formData, reviewsSummary)

                itineraryTextView.text = formatItinerary(generatedItinerary)
                viewModel.setItinerary(generatedItinerary)
            } catch (e: Exception) {
                Log.e("ItineraryScreen", "Erro ao gerar itinerário", e)
                Toast.makeText(this@ItineraryScreenActivity, "Erro ao gerar itinerário", Toast.LENGTH_SHORT).show()
            } finally {
                loadingIndicator.hide()
            }
        }
    }

    private fun loadReviewsData(): String {
        val jsonString: String = assets.open("reviews/reviews.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val reviewsArray = jsonObject.getJSONArray("reviews")
        val reviewsSummary = StringBuilder()
        for (i in 0 until reviewsArray.length()) {
            val review = reviewsArray.getJSONObject(i)
            reviewsSummary.append("Lugar: ${review.getString("Lugar")}, Nota: ${review.getString("Nota")}/5, Avaliação: ${review.getString("Avaliacao")}\n")
        }
        return reviewsSummary.toString()
    }

    private fun simulateApiCall(formData: Itinerario, reviewsSummary: String): String {
        return """
            Dia 1
            Manhã
            - Visite o Parque Ibirapuera: Um dos maiores e mais importantes parques urbanos de São Paulo.
            Tarde
            - Almoço no Mercado Municipal: Experimente o famoso sanduíche de mortadela.
            Noite
            - Jantar na Avenida Paulista: Explore a vibrante vida noturna.
            
            Dia 2
            Manhã
            - Passeio no Bairro da Liberdade: Conheça a maior comunidade japonesa fora do Japão.
            Tarde
            - Visite o Museu de Arte de São Paulo (MASP): Admire a impressionante coleção de arte.
            Noite
            - Show de música ao vivo: Desfrute de uma apresentação em um dos muitos clubes da cidade.
        """.trimIndent()
    }

    private fun formatItinerary(itineraryText: String): CharSequence? {
        val dayRegex = Regex("Dia (\\d+)")
        val days = mutableListOf<Int>()
        dayRegex.findAll(itineraryText).forEach { match ->
            days.add(match.range.first)
        }

        if (days.isEmpty()) {
            days.add(0)
        }

        val formattedDays = StringBuilder()
        for ((dayIndex, startIndex) in days.withIndex()) {
            val endIndex = if (dayIndex < days.size - 1) days[dayIndex + 1] else itineraryText.length
            val dayText = itineraryText.substring(startIndex, endIndex)

            val dayNumber = dayIndex + 1

            val activities = dayText.split("\n")
                .filter { it.trim().isNotEmpty() && !it.startsWith("Dia ") }
                .map { it.trim() }

            formattedDays.append("<h2>Dia $dayNumber</h2>")

            activities.forEach { activity ->
                val cleanedActivity = activity.replace("**", "")
                val finalActivity = cleanedActivity.replace(Regex("(\\d+)\\n(\\d+)"), "$1$2")

                if (finalActivity.contains(Regex("(Manhã|Tarde|Noite)"))) {
                    formattedDays.append("<h3>$finalActivity</h3>")
                } else {
                    val parts = finalActivity.split(":", limit = 2)
                    val description = parts[0].trim()
                    val details = if (parts.size > 1) parts[1].trim() else ""

                    formattedDays.append("<p><b>$description</b></p>")
                    if (details.isNotEmpty()) {
                        formattedDays.append("<p>$details</p>")
                    }
                }
            }
        }

        return HtmlCompat.fromHtml(formattedDays.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun saveItinerary(itinerary: String) {
        lifecycleScope.launch {
            try {
                val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                val savedItineraries = sharedPreferences.getString("itineraries", "[]")
                val itineraries = JSONArray(savedItineraries)
                itineraries.put(itinerary)
                with (sharedPreferences.edit()) {
                    putString("itineraries", itineraries.toString())
                    apply()
                }
                Toast.makeText(this@ItineraryScreenActivity, "Itinerário salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Log.e("ItineraryScreen", "Erro ao salvar itinerário", e)
                Toast.makeText(this@ItineraryScreenActivity, "Erro ao salvar itinerário", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
