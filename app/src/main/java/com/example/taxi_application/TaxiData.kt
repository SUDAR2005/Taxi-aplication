package com.example.taxi_application

data class Taxi(
    val id : Int,
    val driverName : String,
    val mobileNo : String,
    val rating: Float
)
object TaxiData {
    val taxis =  arrayOf(
        Taxi(1, "Ram kumar1", "98735748373", 4.3f),
        Taxi(2, "Ram kumar2", "98765748372", 2.3f),
        Taxi(3, "Ram kumar3", "98765748373", 4.1f),
        Taxi(4, "Ram kumar4", "98765748371", 3.3f)
    )
}