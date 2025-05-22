package com.github.ralfstuckert.kcr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SomeService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() {
        with(log) {// provide context using the with() function
            performOperation("some parameter")
        }
    }

}

context(log:Logger)
fun performOperation(param: String) {
    // do some operation and log result
    log.info("successfully performed operation with param: $param")
}

class OtherService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun otherFunction() {
        context(log) {  // provide context using the context() function
            performOperation("other parameter")
        }
    }
}



interface LoggerContext {
    val log: Logger
}

context(loggerContext:LoggerContext)
val log: Logger
    get() = loggerContext.log


context(loggerContext:LoggerContext)
fun doSomethingInLoggerContext(param: String) {
    log.info("Doing something in ServiceA")
}

class YetAnotherService:LoggerContext {

    override val log = LoggerFactory.getLogger(javaClass)

    fun performB() {
        doSomethingInLoggerContext("some parameter")
    }

}