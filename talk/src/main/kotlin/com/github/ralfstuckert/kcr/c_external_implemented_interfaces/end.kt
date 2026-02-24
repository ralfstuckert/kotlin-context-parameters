package com.github.ralfstuckert.kcr.c_external_implemented_interfaces

/*

fun <T : Comparable<T>> max(x: T, y: T) =
    if (x > y) x else y


context(_: Comparator<T>)
fun <T> max(x: T, y: T) = if (x > y) x else y

context(comparator: Comparator<T>)
operator fun <T> T.compareTo(other: T): Int =
    comparator.compare(this, other)


fun main() {

    println(max(1, 2)) // 2

    val user = User("Ralf")
    val user2 = User("Ralf2")
//    println(max(user, user2)) // does not compile, context missing

    with(userComparator) {
        println(max(user, user2)) // Ralf2
    }

}

val userComparator = Comparator<User> { u1, u2 -> u1.name.length - u2.name.length }

 */
