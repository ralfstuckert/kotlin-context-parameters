# DSLs

Another use case for context parameters given in the Keep is to create resp. extends DSLs. Let's take a look at the 
following example, that let us configure routes for a webserver.

[DSL.kt](../context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/DSL.kt):
```kotlin
typealias RequestHandler = () -> Unit

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

...

server {
    routes {
        addRoute(GET, "/home", { println("GET /home handler") })
        addRoute(POST, "/submit", { println("POST /submit handler") })
    }
}
```

We want to enhance the readability of the DSL by allowing a more natural way to define routes, avoiding the need to 
call a function `addRoute()` explicitly. Our target DSL should look like this:

```kotlin
    routes {
        GET to "/home" handle { println("GET /home handler") }
```

To achieve this, we need two infix functions `to` and `handle`. Kotlin already has an infix function `to` for 
creating pairs

```kotlin
infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
```
so we can use it to create a pair of `HttpMethod` and `String`:

```kotlin
GET to "/home"   
```

What's left is to create the `handle` infix function that takes a `Pair<HttpMethod,String>` and a `RequestHandler`. 
A problem is that the `handle` function needs to be defined in the  `RouteBuilder` so we can add the route. 
Alas, we do not have access to it, since it is third party code. And we cannot define an extension function on 
`RouteBuilder`, since the infix function is alread an extension on `Pair`. But we can use a context parameter 
`RouteBuilder` to define an additional receiver, and now we can call the `addRoute()` function:

```kotlin
context(routeBuilder:RouteBuilder)
infix fun Pair<HttpMethod, String>.handle(handler: () -> Unit) {
    routeBuilder.addRoute(this.first, this.second, handler)
}
```

That's it, our DSL now works as expected :-)

```kotlin
server {
    routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}
```


Next: [Bridge Functions](doc/bridge_functions.md)