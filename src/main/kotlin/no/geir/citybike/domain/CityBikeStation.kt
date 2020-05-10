package no.geir.citybike.domain

data class CityBikeStation constructor(
        val id: Int,
        val stationName: String,
        val availableDocks: Int,
        val availableBikes: Int
        ) {
}

