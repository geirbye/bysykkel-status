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

        val stationsInformationNode: JsonNode = getStatusJsonNode("station_information.json")
        val cityBikeStationNameMap: HashMap<Int, String> = HashMap<Int, String>()
        stationsInformationNode.forEach { node ->
            cityBikeStationNameMap.put(
                    node.path("station_id").asInt(),
                    node.path("name").asText()
            )
        }

        val stationsStatusNode: JsonNode = getStatusJsonNode("station_status.json")
        val bikeStationList : MutableList<CityBikeStation> = ArrayList()
        stationsStatusNode.forEach { node ->
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

    private fun getStatusJsonNode(doc: String): JsonNode {
        val stationStatusJson = osloBysykkelClient.getDocument(doc)
        val stationStatusDocTree: JsonNode = objectMapper.readTree(stationStatusJson)
        return stationStatusDocTree.path("data").path("stations")
    }

}
