# Depencency Injection / Implicit Services
Let's take a look at the following example:

[Logger.kt](../main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/Logger.kt):
```kotlin
class SomeService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() {
        performOperation("some parameter")
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

[Logger.kt](../main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/Logger.kt):
```kotlin
class SomeService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() {
        with(log) {  // provide context using the with() function
            performOperation("some parameter")
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
        context(log) {  // provide context using the context() function
            performOperation("other parameter")
        }
    }
}
```
For sure, the logger is also a parameter, but the signature of the function has not changed and is much cleaner.

Be aware the services use two different functions to provide context: `SomeService` uses the _old_ `with()`
which is always used to pass receivers. But you can only pass one receiver using this function, if you want to 
pass more, you have to nest them:

```kotlin
with(log) {
    with(anotherContext) {
        performOperation("other parameter")
    }
}
```
With the new `context()` function, you can simply write:
```kotlin
context(log, anotherContext) {
    performOperation("other parameter")
}
```
Some folks complaint in the discussion that this might be confusing, as you use the wording `context` for 
both demanding and also providing context, what do you think?

You can use context parameters for properties as well. Let's take a look at the following example. We have a class `Guest`
which has a `firstName` and `lastName` property, which is loaded from a database. We also have a service called `GuestImageUriProvider`
that retrieves the URI of the guest image from another system.

[DependencyInjection.kt](../main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/DependencyInjection.kt):
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