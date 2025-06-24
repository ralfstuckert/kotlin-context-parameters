# Context Parameters
This repository provides examples for the [corresponding article on medium](https://medium.com/@ralf.stuckert/kotlin-context-parameters-0156f2911462)
that let you explore the capabilities of Kotlin context parameters. 

- [Introduction](#Introduction)
- [Use Cases](doc/use_cases.md)
    - [Dependency Injection / Implicit Services](doc/dependency_injection.md)
    - [Scoped Functions](doc/scoped_functions.md)
    - [Externally Implemented Interfaces / Type Classes](doc/type_classes.md)
    - [DSLs](doc/dsl.md)
- [Bridge Functions](doc/bridge_functions.md)
- [Lambdas](doc/lambdas.md)


# Introduction
Context receivers have been around in Kotlin since version 1.6.20 in experimental state,
now they have entered preview state with version 2.2.0 under the name context parameters. The
experimental phase has been used to get a better understanding of the use cases, to introduce
improvements and to clarify the syntax. Let's enter the topic with a recap of extension functions.

## Extension Functions
Extension functions are IMHO one of the most powerful features of Kotlin. It allows you to extend
existing classes, even if you don't have access to the source code. There is no need to bloat classes with
functions needed only in edge cases, just add extension functions to where you need them. 
Also it allows you to create scoped lambdas, which can only be called on a given receiver, which is
the base for creating DSLs in Kotlin. Extension functions are heavily used in the Kotlin standard library,
let's take a the `max()` function as an example.

```kotlin
val x = listOf(1, 2, 3).max()
```

It is not a member function of `List`, but an extension function to `Iterable`. The signature of the function is:

```kotlin
fun <T : Comparable<T>> Iterable<T>.max(): T? 
```

But how is the iterable - the so-called receiver - passed to the function? Let's take a look at the generated java code:

```java
public static final Comparable max(@NotNull Iterable $this$max) 
```
So it is just a static function, which takes the receiver as the first parameter. The `Iterable` class is not touched at
all, that's why you may only access public members of the class.

## Multiple Receivers
No doubt extension functions are quite powerful, so what's missing? Let's see the [orginal feature request by
Damian Wieczorek](https://youtrack.jetbrains.com/issue/KT-10468) for multiple receivers. The idea is to allow
specifying multiple receivers for a function, so you can use them in the body of the function.

_This can be extremely useful when dealing with third-party libraries or frameworks which require extending their own classes.
Currently, the common thing to do is implement them within an interface that must be implemented in each class._

_For example (in Android), to create an extension function Float.dp() to use within a View:_

```kotlin
fun (View, Float).dp() = this * resources.displayMetrics.density
// explicitly: fun (View, Float).dp() = this@Float * this@View.resources.displayMetrics.density

class SomeView : View {
    val someDimension = 4f.dp()
}
```

The idea was to be able to write extension functions that have access to an additional receiver, which is passed
as some kind of scope. The first implementation of [context receivers](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md),
as they were called at that time, allowed specifying multiple receivers for a (extension) function. Let's see how
it works in practice using the example above.

[ViewExample.kt](../main/context-receivers/src/main/kotlin/com/github/ralfstuckert/kcr/ViewExample.kt):

```kotlin
context(View)
fun Float.dp()= this * resources.displayMetrics.density
//        this@Float * this@View.resources.displayMetrics.density

class SomeView : View {
    val someDimension = 4f.dp()
}
```

So context defines a kind of scope for the (extension) function, which must be present to call the function.
Technically, the context receivers are passed as additional parameters to the function: at first the context receivers,
than the (optional) receiver, followed by the regular parameters. So the generated java code looks like this:

```java
   public static final float getDp(@NotNull View $context_receiver_0, float $this$dp) 
```


## Context Parameters
Context receivers have been experimental for quite a while, and this time has been used for clarification of
the use cases. Besides some restrictions, the main difference is that the parameters are now named. Using
our example above, we must write:

[ViewExample.kt](../main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/ViewExample.kt):
```kotlin
context(view:View)
fun Float.dp()= this * view.resources.displayMetrics.density
```
So you must explictly use the named reference `view` to access the context receiver, you cannot use `this@View` anymore.
At least if you need to reference the context receiver in the body of the function, if you just pass it as a
scope for calling other functions, you can use anonymous parameters. Let’s add a `Int.dp()` variant that just 
delegates to the `Float` extension. Since we do not reference the receiver (but just pass it), we can declare 
the context parameter as anonymous:

```kotlin
context(_:View)
fun Int.dp() = this.toFloat().dp()

class SomeView : View {
  val someDimension = 4.dp()
}
```

Next: [Use Cases](doc/use_cases.md)
