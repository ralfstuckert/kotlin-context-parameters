# Depencency Injection / Implicit Services
Let's take a look at the following example:

[Logger.kt](../blob/main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/Logger.kt):
```kotlin
class SomeService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() {
        val parameter = TODO("get parameter from somewhere")
       performOperation(parameter)
    }
    
    fun performOperation(param: String) {
        // do some operation and log result
        log.info("successfully performed operation with param: $param")
    }
}
```

Now you have another service that like to reuse the `performOperation` function, but you don't want to depend on the
`SomeService` class and for sure don't want to copy the code. You could extract it as a function, but then you have to pass
the logger as a parameter, which is not very nice, since it is not a 'real' parameter of the function, but more like a
technical dependency needed by the implementation of function. You could use a global logger, but that is not a good idea
either, since you want to use a dedicated logger for each class. Here is where context parameters come into play.

[Logger.kt](../blob/main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/Logger.kt):
```kotlin
class SomeService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() {
        val parameter = TODO("get parameter from somewhere")
        with(log) {
            performOperation(parameter)
        }
    }

}

context(log:Logger)
fun performOperation(param: String) {
    // do some operation and log result
    log.info("successfully performed operation with param: $param")
}

class OtherService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun otherFunction() {
        val parameter = TODO("get parameter from somewhere")
        with(log) {
            performOperation(parameter)
        }
    }
}
```
For sure, the logger is also a parameter, but the signature of the function has not changed and is much cleaner.

You can use context parameters for properties as well. Let's take a look at the following example. We have a class `Guest`
which has a `firstName` and `lastName` property, which is loaded from a database. We also have a service called `GuestImageUriProvider`
that retrieves the URI of the guest image from another system.

[DependencyInjection.kt](../blob/main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/DependencyInjection.kt):
```kotlin
data class Guest(val id:GuestId, val firstName: String, val lastName: String)

interface GuestImageUriProvider {
    fun getGuestImageUri(guestId: GuestId): URI
}
```

We could write an extension function to retrieve the URI of the guest image passing `GuestImageUriProvider` as a context parameter

```kotlin
context(guestImageUriProvider: GuestImageUriProvider)
fun Guest.getImageUri() =
    guestImageUriProvider.getGuestImageUri(id)
```

But we could as well write a property in the `Guest` class, which is much cleaner and easier to read:

```kotlin
context(guestImageUriProvider: GuestImageUriProvider)
val Guest.imageUri: URI
    get() = guestImageUriProvider.getGuestImageUri(id)

...
with(guestImageUriProvider) {
    println("Guest ${guest.lastName} with image URI: ${guest.imageUri}")
}
```

Next: [Scoped Functions](scoped_functions.md)