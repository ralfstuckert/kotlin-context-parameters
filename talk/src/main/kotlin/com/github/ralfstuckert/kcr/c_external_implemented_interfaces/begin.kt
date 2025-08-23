package com.github.ralfstuckert.kcr.c_external_implemented_interfaces

import com.github.ralfstuckert.kcr.b_scoped_functions.User


fun <T : Comparable<T>> max(x: T, y: T) =
    if (x > y) x else y

// region generic max
// fun <T> max(x: T, y: T) = if (x > y) x else y
// endregion


fun main() {

    println(max(1, 2)) // 2

    // region max user
    val user = User("Ralf")
    val user2 = User("Ralf2")
//    println(max(user, user2))
    // endregion
}


