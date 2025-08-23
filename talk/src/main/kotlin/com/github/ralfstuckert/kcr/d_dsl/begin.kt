package com.github.ralfstuckert.kcr.d_dsl

import com.github.ralfstuckert.kcr.HttpMethod
import com.github.ralfstuckert.kcr.HttpMethod.*
import com.github.ralfstuckert.kcr.RouteBuilder
import com.github.ralfstuckert.kcr.server

val oldServer = server {
    routes {
        addRoute(GET, "/home", { println("GET /home handler") })
        addRoute(POST, "/submit", { println("POST /submit handler") })
    }
}

val newServer = server {
    routes {
//        GET to "/home" handle { println("GET /home handler") }
//        POST to "/submit" handle { println("POST /submit handler") }
    }
}



