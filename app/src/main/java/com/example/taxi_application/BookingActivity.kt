import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.taxi_application.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import com.example.taxi_application.SharedPreferenceHelper
import com.example.taxi_application.NotificationHelper
import com.example.taxi_application.TaxiData

class BookingActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay
    private lateinit var sharedPreferencesHelper: SharedPreferenceHelper
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        Configuration.getInstance().load(this, getSharedPreferences("osm", MODE_PRIVATE))
        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        locationOverlay.enableMyLocation()
        map.overlays.add(locationOverlay)
        val compassOverlay = CompassOverlay(this, InternalCompassOrientationProvider(this), map)
        compassOverlay.enableCompass()
        map.overlays.add(compassOverlay)
        locationOverlay.runOnFirstFix {
            runOnUiThread {
                val startPoint = GeoPoint(locationOverlay.myLocation)
                map.controller.animateTo(startPoint)
                map.controller.setZoom(15.0)
            }
        }
        sharedPreferencesHelper = SharedPreferenceHelper(this)
        notificationHelper = NotificationHelper(this)
        val spinner: Spinner = findViewById(R.id.taxiSpinner)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            TaxiData.taxis.map{ "${it.driverName} - ${it.driverName} (${it.id} km)" }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val bookButton: Button = findViewById(R.id.confirmBookingButton)
        bookButton.setOnClickListener {
            showConfirmationDialog(spinner.selectedItemPosition)
        }
    }
    private fun showConfirmationDialog(selectedTaxiIndex: Int) {
        val selectedTaxi = TaxiData.taxis[selectedTaxiIndex]
        AlertDialog.Builder(this)
            .setTitle("Confirm Booking")
            .setMessage("Book ${selectedTaxi.driverName} in ${selectedTaxi.id}?")
            .setPositiveButton("Confirm") { _, _ ->
                sharedPreferencesHelper.saveLastPref(selectedTaxi)
                notificationHelper.sendNotification(selectedTaxi)
                Toast.makeText(this, "Taxi booked successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}