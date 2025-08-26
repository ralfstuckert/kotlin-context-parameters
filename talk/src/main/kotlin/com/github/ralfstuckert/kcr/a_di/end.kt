package com.github.ralfstuckert.kcr.a_di

/*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.LoggerFactory.getLogger


// region some service
class SomeService {

    private val logger = getLogger(javaClass)

    fun someFunction() = with(logger) {
        performOperation("some parameter")
    }

}
// endregion

context(logger: Logger)
fun performOperation(param: String) {
    // do some operation and log result
    logger.info("successfully performed operation with param: $param")
}


// region other service
class OtherService {
    private val logger = getLogger(javaClass)

    fun otherFunction() {
        context(logger) {
            // use the context logger to perform an operation
            performOperation("other parameter")
        }
    }
}
// endregion


 */

