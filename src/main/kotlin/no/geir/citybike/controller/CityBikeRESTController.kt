package no.geir.citybike.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import no.geir.citybike.domain.CityBikeStationList
import no.geir.citybike.service.CityBikeService
import javax.inject.Inject

@Controller("/citybike")
open class CityBikeRESTController(@Inject val cityBikeService: CityBikeService) {

    @Get("/bike-stations", produces = [MediaType.APPLICATION_JSON])
    @Produces(MediaType.APPLICATION_JSON)
    open fun getCityBikeStationUnformatted(): HttpResponse<CityBikeStationList> {

        val cityBikeStationList = cityBikeService.getBikeStationList()
        val cityBikeStationListWrapper = CityBikeStationList(cityBikeStationList)
        return HttpResponse.ok(cityBikeStationListWrapper)

    }

}

