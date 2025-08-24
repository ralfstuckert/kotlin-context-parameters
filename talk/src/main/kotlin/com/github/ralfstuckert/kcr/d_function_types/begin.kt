package com.github.ralfstuckert.kcr.d_function_types

import com.github.ralfstuckert.kcr.b_scoped_functions.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory


val lambda: (User) -> Unit = { user ->
//    logger.info("user is $user")
}


fun main() {

    val logger = LoggerFactory.getLogger("console")
    val user = User("Ralf")

    lambda(user)

}

