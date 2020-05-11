package no.geir.citybike.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
open class GlobalExceptionHandler : ExceptionHandler<Exception, MutableHttpResponse<String>> {

    override fun handle(request: HttpRequest<*>?, exception: Exception?): MutableHttpResponse<String> {
        return HttpResponse.serverError("En feil oppstod, prøv igjen senere")
    }
}