package com.github.ralfstuckert.kcr

interface Request
interface Response

//typealias RequestHandler = (Request, Response) -> Unit
typealias RequestHandler = () -> Unit


sealed class HttpMethod(val name:String) {
    object GET: HttpMethod("GET")
    object POST: HttpMethod("POST")
    object PUT: HttpMethod("PUT")
    object DELETE: HttpMethod("DELETE")
}

data class Route(
    val method: HttpMethod,
    val path: String,
    val handler: RequestHandler)

class RouteBuilder {
    val routes = mutableListOf<Route>()

    fun addRoute(method: HttpMethod, path: String, handler: RequestHandler) {
        routes += Route(method, path, handler)
    }
}

fun routes(configure: RouteBuilder.() -> Unit) {
    RouteBuilder().apply(configure)
}

