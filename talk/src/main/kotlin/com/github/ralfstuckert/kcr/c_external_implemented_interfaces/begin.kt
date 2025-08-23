package com.github.ralfstuckert.kcr.c_external_implemented_interfaces

import com.github.ralfstuckert.kcr.d_function_types.User


fun <T : Comparable<T>> max(x: T, y: T) =
    if (x > y) x else y

// region generic max
// fun <T> max(x: T, y: T) = if (x > y) x else y
// endregion

// region contextual compareTo
/*
context(comparator: Comparator<T>)
operator fun <T> T.compareTo(other: T): Int =
    comparator.compare(this, other)
 */
// endregion

// region User comparator
val userComparator = object : Comparator<User> {
    override fun compare(o1: User, o2: User): Int {
        // comparing name by length does not make much sense ;-)
        return o1.name.length - o2.name.length
    }
}
// endregion


fun main() {

    println(max(1, 2)) // 2

    // region max user
    val user = User("Ralf")
    val user2 = User("Ralf2")
//    println(max(user, user2))
    // endregion
}


