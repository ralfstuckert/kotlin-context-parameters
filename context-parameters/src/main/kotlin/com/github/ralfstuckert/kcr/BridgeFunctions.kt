package com.github.ralfstuckert.kcr

import com.github.ralfstuckert.kcr.HttpMethod.GET
import com.github.ralfstuckert.kcr.HttpMethod.POST


context(server:ServerBuilder)
fun addServerConfig() {
    routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}

context(server:ServerBuilder)
fun routes(block: RouteBuilder.() -> Unit) = server.routes(block)

