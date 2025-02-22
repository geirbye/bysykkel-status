package no.geir.citybike.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.views.ModelAndView
import no.geir.citybike.domain.CityBikeStationList
import no.geir.citybike.service.CityBikeService
import javax.inject.Inject

@Controller("/citybike")
open class CityBikeWEBController(@Inject val cityBikeService: CityBikeService) {

    @Get("/html")
    @Produces(MediaType.TEXT_HTML)
    open fun getCityBikeStationFormatted(): ModelAndView<CityBikeStationList> {

        val cityBikeStationList = cityBikeService.getBikeStationList()
        val cityBikeStationListWrapper = CityBikeStationList(cityBikeStationList)
        return  ModelAndView("bysykkel", cityBikeStationListWrapper)

    }

}

