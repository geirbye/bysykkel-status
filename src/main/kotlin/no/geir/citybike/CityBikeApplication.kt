package no.geir.citybike

import io.micronaut.runtime.Micronaut

object CityBikeApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("no.geir.citybike")
                .mainClass(CityBikeApplication.javaClass)
                .start()
    }
}
