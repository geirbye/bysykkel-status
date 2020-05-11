package no.geir.citybike.service

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OsloBysykkelClient  {

    companion object {
        private val log = LoggerFactory.getLogger(OsloBysykkelClient::class.java)
    }

    @field:Property(name = "bysykkel.client_identifier")
    lateinit var bysykkelClientId : String

    @field:Client("\${bysykkel.url}")
    @Inject lateinit var httpClient: RxHttpClient

    fun getDocument(doc: String) : String {

        try {
            val retrieve = httpClient.retrieve(GET<Any>(doc).header("Client-Identifier", bysykkelClientId)).blockingFirst()
            return retrieve.toString()
        } catch (e: HttpClientResponseException) {
            log.error("Exception caught: $e" )
            throw e
        }
    }

}
