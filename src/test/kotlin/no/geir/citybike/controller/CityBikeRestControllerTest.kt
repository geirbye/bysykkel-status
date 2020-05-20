package no.geir.citybike.controller

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.mockk.impl.annotations.MockK
import io.mockk.every
import io.mockk.junit5.MockKExtension
import no.geir.citybike.domain.CityBikeStation
import no.geir.citybike.service.CityBikeService
import no.geir.citybike.util.TestUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URI
import javax.inject.Inject

@MicronautTest
@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationControllerTest {

    @Inject
    private lateinit var server: EmbeddedServer

    @MockK
    private lateinit var cityBikeService: CityBikeService

    @Inject
    @field:Client("/")
    private lateinit var client: RxHttpClient

    @Inject
    private lateinit var objectMapper: ObjectMapper

    private lateinit var cityBikeStationList: List<CityBikeStation>

    @BeforeAll
    fun init() {
        cityBikeStationList = TestUtils.defaultCityBikeStationList()
        server.applicationContext.registerSingleton(CityBikeService::class.java, cityBikeService)
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    }

    @Test
    fun testGetBikeStationList() {
        every { cityBikeService.getBikeStationList() } returns cityBikeStationList

        val req: HttpRequest<List<CityBikeStation>> = HttpRequest.GET(URI.create("/citybike/bike-stations"))
        val response = client.toBlocking().retrieve(req)
        val cityBikeStationList = objectMapper.readTree(response).get("cityBikeStationList")

        Assertions.assertNotNull(response)
        Assertions.assertTrue(cityBikeStationList.isArray())
        Assertions.assertEquals("testStation123", cityBikeStationList[0].path("stationName").asText())
        Assertions.assertEquals(13, cityBikeStationList[1].path("availableBikes").asInt())
    }

}