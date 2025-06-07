package com.github.ralfstuckert.kcr

import com.github.ralfstuckert.kcr.HttpMethod.*

val oldServer = server {
    routes {
        addRoute(GET, "/home", { println("GET /home handler") })
        addRoute(POST, "/submit", { println("POST /submit handler") })
    }
}


context(routeBuilder:RouteBuilder)
infix fun Pair<HttpMethod, String>.handle(handler: () -> Unit) {
    routeBuilder.addRoute(this.first, this.second, handler)
}

val newServer = server {
    routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}



