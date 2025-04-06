package com.example.taxi_application
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {
    private val pref : SharedPreferences = context.getSharedPreferences("taxi", Context.MODE_PRIVATE)
    fun saveLastPref(taxi: Taxi){
        with(pref.edit()){
            putInt("Taxi_id", taxi.id)
            putString("Taxi_driver", taxi.driverName)
            apply()
        }
    }

    fun getPref() : String{
        val driverName = pref.getString("Taxi_driver", null)
        return "Your last ride was with $driverName"
    }
}
