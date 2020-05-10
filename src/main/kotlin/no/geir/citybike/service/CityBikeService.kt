package no.geir.citybike.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import no.geir.citybike.domain.CityBikeStation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityBikeService (@Inject val osloBysykkelClient: OsloBysykkelClient,
                       @Inject val objectMapper: ObjectMapper) {

    fun getBikeStationList(): List<CityBikeStation> {

        val stationInformationJson = osloBysykkelClient.getDocument("station_information.json")
        val stationInformationDocTree: JsonNode = objectMapper.readTree(stationInformationJson)
        val stationsInformationNode: JsonNode = stationInformationDocTree.path("data").path("stations")

        val cityBikeStationNameMap: HashMap<Int, String> = HashMap<Int, String>()
        for (node in stationsInformationNode) {
            cityBikeStationNameMap.put(
                    node.path("station_id").asInt(),
                    node.path("name").asText()
                    )
        }

        val stationStatusJson = osloBysykkelClient.getDocument("station_status.json")
        val stationStatusDocTree: JsonNode = objectMapper.readTree(stationStatusJson)
        val stationsStatusNode: JsonNode = stationStatusDocTree.path("data").path("stations")

        val bikeStationList : MutableList<CityBikeStation> = ArrayList()

        for (node in stationsStatusNode) {
            val stationId : Int  = node.path("station_id").asInt()
            val stationItem = CityBikeStation(
                                stationId,
                                cityBikeStationNameMap.get(stationId)!!,
                                node.path("num_docks_available").intValue(),
                                node.path("num_bikes_available").intValue()
            )
            bikeStationList.add(stationItem)
        }

        bikeStationList.sortWith(Comparator.comparing(CityBikeStation::stationName))

        return bikeStationList

    }

}
