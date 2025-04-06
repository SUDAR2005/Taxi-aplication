import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.taxi_application.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferencesHelper: SharedPreferenceHelper
    private lateinit var lastBookingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesHelper = SharedPreferenceHelper(this)
        lastBookingText = findViewById(R.id.lastBookingText)
        lastBookingText.text = sharedPreferencesHelper.getPref()
        registerForContextMenu(lastBookingText)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_book -> {
                startActivity(Intent(this, BookingActivity::class.java))
                true
            }
            R.id.menu_about -> {
                showPopupMenu(findViewById(R.id.lastBookingText))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.context_clear -> {
                sharedPreferencesHelper.saveLastPref(Taxi(0, "None", "N",3.2f))
                lastBookingText.text = sharedPreferencesHelper.getPref()
                Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.popup_about -> {
                    AlertDialog.Builder(this)
                        .setTitle("About Taxi Booking App")
                        .setMessage("This is the application.")
                        .setPositiveButton("OK", null)
                        .show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}