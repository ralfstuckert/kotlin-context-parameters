# Externally implemented Interfaces / Type Classes

Externally implemented interfaces allows you to provide an implementation of an interface for a given type, without
the need to modify the type itself. In Haskell this is done using type classes, if you are familiar with Rust, think
of traits. You can do something similar in Kotlin using context parameters. We will take the `Comparable` and
`Comparator` interfaces to illustrate this. We have a function `max()` that takes two parameters of type `T` and
returns the maximum of the two. The type `T` must implement the `Comparable` interface, so we can compare the two values.

[TypeClasses.kt](../blob/main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/TypeClasses.kt):
```kotlin
fun <T : Comparable<T>> max(x: T, y: T) = if (x > y) x else y
```

But what if we want to compare two values of a type that does not implement the `Comparable` interface? We get a compile
error, since the type `T` does not implement the `Comparable` interface.

```kotlin
fun <T> max(x: T, y: T) = if (x > y) x else y
//                              ^ 
// Unresolved reference. None of the following candidates is
// applicable because of a receiver type mismatch:
// fun String.compareTo(other: String, ignoreCase: Boolean = ...): Int
```

There is no generic `compareTo()` function, so we have to implement our own: If we have a `Comparator` for the type `T`,
we can use it to compare the two values. And that's where context parameters come into play:

```kotlin
context(comparator: Comparator<T>)
operator fun <T> T.compareTo(other: T): Int = comparator.compare(this, other)
```

So if we now add the `Comparator` as a context parameter to our `max()` function, we can use the generic `compareTo()`
function:

```kotlin
context(_: Comparator<T>)
fun <T> max(x: T, y: T) = if (x > y) x else y  // compiler is satified
```

Now we can use the `max()` function with any type, as long as we provide a `Comparator` for the type. Let's take a look
at the following example:

```kotlin
// our type User is not comparable 
data class User(val name: String)

// we have an externally implemented comparator for User
val userComparator = object : Comparator<User> {
 override fun compare(o1: User, o2: User): Int {
  return o1.name.length - o2.name.length
 }
}

val user = User("Ralf")
val user2 = User("Ralf2")

// we can use the generic max function in the context of the comparator
context(userComparator) {
   println(max(user, user2)) // Ralf2
}
```

Okay, comparing users by their name length is not very useful, but you get the idea. You can call the function with
any type as long as you provide a `Comparator` for it. 

Next: [DSLs](dsl.md)