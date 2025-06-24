# Bridge Functions
One (of the) complaint(s) that came with the change from context receivers to parameters, is that you have to name them
in order to use them. This might be undesirable in certain situations like DSLs. Take the following function using the
DSL from above that works fine with former context receivers:

```kotlin
context(ServerBuilder)
fun addServerConfig() {
    routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}
```

With context parameters, you have to explicitly name the `server` parameter and reference it; there is no implicit receiver:

```kotlin
context(server:ServerBuilder)
fun addServerConfig() {
    server.routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}
```

To come around this situation, the Keep suggests writing a so-called bridge function, which does the dereferencing
for you:

[BridgeFunctions.kt](../context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/BridgeFunctions.kt):

```kotlin
context(server:ServerBuilder)
fun routes(block: RouteBuilder.() -> Unit) = server.routes(block)

context(server:ServerBuilder)
fun addServerConfig() {
    routes {
        GET to "/home" handle { println("GET /home handler") }
        POST to "/submit" handle { println("POST /submit handler") }
    }
}
```

This is tedious, but if you need that, there is at least a workaround.

