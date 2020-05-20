package no.geir.citybike.util

import no.geir.citybike.domain.CityBikeStation

class TestUtils {

    companion object {

        fun defaultCityBikeStationList(): List<CityBikeStation> {
            return arrayListOf(CityBikeStation(123, "testStation123", 5, 10),
                    CityBikeStation(124, "testStation124", 8, 13))
        }
    }

}