package com.github.ralfstuckert.kcr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServiceA {

    private val log = LoggerFactory.getLogger(javaClass)

    fun performA() {
        with(log) {
            doSomethingWithLogger("some parameter")
        }
    }
}

context(Logger)
fun doSomethingWithLogger(param: String) {
    info("Doing something in ServiceA")
}


interface LoggerContext {
    val log: Logger
}

context(LoggerContext)
fun doSomethingInLoggerContext(param: String) {
    log.info("Doing something in ServiceA")
}

class ServiceB:LoggerContext {

    override val log = LoggerFactory.getLogger(javaClass)

    fun performB() {
        doSomethingInLoggerContext("some parameter")
    }

}