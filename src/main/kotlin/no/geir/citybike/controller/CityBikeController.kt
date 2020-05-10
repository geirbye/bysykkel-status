package no.geir.citybike.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.views.ModelAndView
import no.geir.citybike.domain.CityBikeStationList
import no.geir.citybike.service.CityBikeService
import javax.inject.Inject

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/citybike")
open class CityBikeController(@Inject val cityBikeService: CityBikeService) {

    @Get("/unformatted", produces = [MediaType.APPLICATION_JSON])
    @Produces(MediaType.APPLICATION_JSON)
    open fun getCityBikeStationUnformatted(): HttpResponse<String> {

        val cityBikeStationList = cityBikeService.getBikeStationList()
        return HttpResponse.ok(cityBikeStationList.toString())

    }

    @Get("/formatted")
    open fun getCityBikeStationFormatted(): ModelAndView<CityBikeStationList> {

        val cityBikeStationList = cityBikeService.getBikeStationList()
        val cityBikeStationListWrapper = CityBikeStationList(cityBikeStationList)
        return  ModelAndView("bysykkel", cityBikeStationListWrapper)

    }

}

