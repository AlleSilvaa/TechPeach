import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.cp.myapplication.R
import com.cp.myapplication.model.Itinerario
import com.google.android.material.appbar.MaterialToolbar

class ItineraryDetailsActivity : AppCompatActivity() {

    private lateinit var itinerary: Itinerario
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_details)

        toolbar = findViewById(R.id.toolbar)
        val backButton: ImageView = findViewById(R.id.button_back)
        val itineraryTextView: TextView = findViewById(R.id.itinerary_text)

        // Obtendo o itinerário da intent
        itinerary = intent.getParcelableExtra("itinerary") ?: return

        // Configuração da toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        backButton.setOnClickListener {
            finish()
        }

        // Definir o texto do itinerário
        itineraryTextView.text = formatItinerary(itinerary.descricao)
    }

    private fun formatItinerary(itineraryText: String?): CharSequence? {
        if (itineraryText.isNullOrEmpty()) return null

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
}
