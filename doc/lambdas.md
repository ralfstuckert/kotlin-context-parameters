# Lambdas
We have already seen that we can pass context parameters to lambdas in the part on 
[scoped functions](./scoped_functions.md). But how can we actually use them in lambdas. Let's
start with a lambda that takes two context parameters:

```kotlin
val lambda1 : context(Logger, User) () -> Unit = {
    // TODO use logger and user
}
```
As you can see, they are not named, so do I reference them to e.g. log the given user? The Kotlin
standard lib provides a helper function that allows us to retrieve the context parameter of a given type:

```kotlin
context(context: A)
inline fun <A> contextOf(): A = context
```
The trick is that this function uses a named reference `context` which then used as the return value.
We can use this function to retrieve the context parameters in our lambda:

```kotlin
val lambda1 : context(Logger, User) () -> Unit = {
    val log = contextOf<Logger>()
    val user = contextOf<User>()
    log.info("user is $user")
}
```

Now how do we actually pass these context parameters to the lambda? For the lambda these are just parameters,
so we can call it in two ways: either pass them as context or as direct parameters:

```kotlin
    val logger = LoggerFactory.getLogger("console")
    val user = User("Ralf")

    // pass them as context
    context(logger, user) {
        lambda1()
    }
    
    // pass them as parameters
    lambda1(logger, user)
```

But you cannot mix it up and pass the logger as a context and the user as a direct parameter.
Let's do some variants of context and other parameters. `lambda2` get the `Logger`
passed as a context parameter and the `User` as an extension receiver:

```kotlin
val lambda2 : context(Logger) User.() -> Unit = {
    val log = contextOf<Logger>()
    val user = this
    log.info("user is $user")
}
```
Again, you can call it two ways:

```kotlin
    context(logger) {
        user.lambda2()
    }

    lambda2(logger, user)
```

Next lambda gets the `Logger` as context, and the `User` as a regular parameter:

```kotlin
val lambda3 : context(Logger) (User) -> Unit = { user ->
    val log = contextOf<Logger>()
    log.info("user is $user")
}
```

As you already expected, you may call it two ways:
```kotlin
    context(logger) {
        lambda3(user)
    }

    lambda3(logger, user)
```

Now the `Logger` as an extension receiver and the `User` as a regular parameter.

```kotlin
val lambda4 : Logger.(User) -> Unit = {     user ->
val log: Logger = this
log.info("user is $user")
}
```

Now we don't have context parameters anymore, but we can still call it two ways:
```kotlin
    logger.lambda4(user)

    lambda4(logger, user)
```

And finally, with two regular parameters:

```kotlin
val lambda5 : (Logger, User) -> Unit = { log, user ->
    log.info("user is $user")
}
```

Since we just have regular parameters, there is just one way to call it:

```kotlin
    lambda5(logger, user)
```

So dependending on the scope and context of the call (pun intended) it will be obvious how to pass
parameters to lambdas, but there will be situations where it might be helpful to the alternative.