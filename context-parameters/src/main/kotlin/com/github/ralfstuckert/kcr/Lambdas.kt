package com.github.ralfstuckert.kcr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class User(val name: String)

// these are all equivalent types
val lambda1 : context(Logger, User) () -> Int = {
    val logger = implicit<Logger>()
    val user = implicit<User>()
    1
}
val lambda2 : context(Logger) User.() -> Int = {
    val logger = implicit<Logger>()
    val user = this
    1
}
val lambda3 : context(Logger) (User) -> Int = { user ->
    val logger = implicit<Logger>()
    1
}
val lambda4 : Logger.(User) -> Int = {     user ->
    val logger: Logger = this
    1
}
val lambda5 : (Logger, User) -> Int = { logger, user ->
    1
}



fun main() {
    val logger = LoggerFactory.getLogger("console")
    val user = User("Ralf")


    context(logger, user) {
        lambda1()
    }
    lambda1(logger, user)

    context(logger) {
        user.lambda2()
    }
    lambda2(logger, user)

    context(logger) {
        lambda3(user)
    }
    lambda3(logger, user)

    logger.lambda4(user)
    lambda4(logger, user)

    lambda5(logger, user)

    withConsoleLogger {
        // you can use 'implicit' to access the context parameter
        implicit<Logger>().info("hello")

        "hi"
    }

}

fun <A> withConsoleLogger(block: context(Logger) (String) -> A):A =
    block(LoggerFactory.getLogger("console"), "hello")



