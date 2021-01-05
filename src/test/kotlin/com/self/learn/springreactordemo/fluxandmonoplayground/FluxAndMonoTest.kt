package com.self.learn.springreactordemo.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.lang.RuntimeException

class FluxAndMonoTest {

    @Test
    internal fun `flux test`() {
        val stringFlux = Flux.just("spring", "spring boot", "reactive spring").log()


        StepVerifier.create(stringFlux)
            .expectSubscription()
            .expectNext("spring", "spring boot", "reactive spring")
            .verifyComplete()
    }

    @Test
    internal fun `flux test error`() {
        val stringFlux = Flux.just("spring", "spring boot", "reactive spring")
            .concatWith(Flux.error(RuntimeException("Exception occured")))
            .concatWith(Flux.just("afterError"))
            .log()

        StepVerifier
            .create(stringFlux)
            .expectSubscription()
            .expectNextCount(3)
            //.expectNext("spring", "spring boot", "reactive spring")
            .expectErrorMessage("Exception occured")
            //.expectError(RuntimeException::class.java)
            .verify()

    }
}