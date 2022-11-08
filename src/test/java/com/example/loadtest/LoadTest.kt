package com.example.loadtest

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl

class LoadTest : Simulation() {
    var httpProtocol = HttpDsl.http
        .baseUrl("http://localhost:7071") // Here is the root for all relative URLs
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .doNotTrackHeader("1")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .acceptEncodingHeader("gzip, deflate")
        .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
    var scn = CoreDsl.scenario("Hello function load test") // Test the Azure Function
        .exec(HttpDsl.http("hello")["/api/hello"])

    init {
        setUp(
            scn.injectOpen(CoreDsl.atOnceUsers(10))
        ).protocols(httpProtocol)
    }
}