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

data class Server(val routes: List<Route>)

class RouteBuilder {
    private val routes = mutableListOf<Route>()

    fun addRoute(method: HttpMethod, path: String, handler: RequestHandler) {
        routes += Route(method, path, handler)
    }

    fun build(): List<Route> = routes.toList()
}

class ServerBuilder {
    private val routeBuilder = RouteBuilder()

    fun routes(block: RouteBuilder.() -> Unit) {
        routeBuilder.apply(block)
    }

    fun build(): Server = Server(routeBuilder.build())
}

fun server(block: ServerBuilder.() -> Unit): Server =
    ServerBuilder().apply(block).build()

