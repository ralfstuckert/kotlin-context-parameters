package com.github.ralfstuckert.kcr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class User(val name: String)

// these are all equivalent types
val lambda1 : context(Logger, User) () -> Unit = {
    val log = contextOf<Logger>()
    val user = contextOf<User>()
    log.info("user is $user")
}
val lambda2 : context(Logger) User.() -> Unit = {
    val log = contextOf<Logger>()
    val user = this
    log.info("user is $user")
}
val lambda3 : context(Logger) (User) -> Unit = { user ->
    val log = contextOf<Logger>()
    log.info("user is $user")
}
val lambda4 : Logger.(User) -> Unit = {     user ->
    val log: Logger = this
    log.info("user is $user")
}
val lambda5 : (Logger, User) -> Unit = { log, user ->
    log.info("user is $user")
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

    val result = withConsoleLogger {
        // you can use 'contextOf' to access the context parameter
        contextOf<Logger>().info("hello")

       42
    }

}

fun <A> withConsoleLogger(block: context(Logger) () -> A):A =
    block(LoggerFactory.getLogger("console"))
