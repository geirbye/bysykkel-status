package no.geir.citybike.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.micronaut.http.HttpResponseFactory
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import no.geir.citybike.util.TestUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CityBikeServiceTest {

    private lateinit var service : CityBikeService

    @MockK
    private lateinit var client : OsloBysykkelClient

    private lateinit var objectMapper : ObjectMapper
    private lateinit var osloBysykkelInformationResponse : String
    private lateinit var osloBysykkelStatusResponse : String

    @BeforeAll
    fun init() {
        objectMapper = jacksonObjectMapper()
        osloBysykkelInformationResponse = TestUtils.defaultOsloBysykkelInformationResponse()
        osloBysykkelStatusResponse = TestUtils.defaultOsloBysykkelStatusResponse()
        service = CityBikeService(client, objectMapper)
    }

    @Test
    fun testGetBikeStationList() {
        every { client.getDocument("station_information.json") } returns osloBysykkelInformationResponse
        every { client.getDocument("station_status.json") } returns osloBysykkelStatusResponse

        val response = service.getBikeStationList()

        Assertions.assertNotNull(response)
        Assertions.assertEquals(3, response.size)
        Assertions.assertEquals(4, response[0].availableBikes)
        Assertions.assertEquals(5, response[1].availableDocks)
        Assertions.assertEquals("Sotahjørnet", response[2].stationName)
    }

    @Test
    fun testGetBikeStationListFailsWithException() {
        every { client.getDocument(any()) } throws HttpClientResponseException("Error", HttpResponseFactory.INSTANCE.status<HttpStatus>(HttpStatus.valueOf(500)))
        val exception = Assertions.assertThrows(HttpClientResponseException::class.java) {
            service.getBikeStationList()
        }
        Assertions.assertEquals(exception.status, HttpStatus.valueOf(500))
    }

 }