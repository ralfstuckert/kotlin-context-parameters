package com.github.ralfstuckert.kcr.e_bridge_functions

import com.github.ralfstuckert.kcr.HttpMethod.GET
import com.github.ralfstuckert.kcr.HttpMethod.POST
import com.github.ralfstuckert.kcr.RouteBuilder
import com.github.ralfstuckert.kcr.ServerBuilder

/*

context(server: ServerBuilder)
fun addServerConfig() {
    routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}

context(server: ServerBuilder)
fun routes(block: RouteBuilder.() -> Unit) = server.routes(block)

 */
