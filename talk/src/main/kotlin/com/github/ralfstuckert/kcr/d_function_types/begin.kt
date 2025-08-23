package com.github.ralfstuckert.kcr.d_function_types

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class User(val name: String)

val lambda : (User) -> Unit = { user ->
//    log.info("user is $user")
}



fun main() {

    val logger = LoggerFactory.getLogger("console")
    val user = User("Ralf")


    context(logger) {
        lambda(user)
    }

}

