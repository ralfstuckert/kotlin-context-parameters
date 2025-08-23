package com.github.ralfstuckert.kcr.a_di

/*

// region some service
class SomeService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() = with(log) {
        performOperation("some parameter")
    }

}
// endregion

context(log: Logger)
fun performOperation(param: String) {
    // do some operation and log result
    log.info("successfully performed operation with param: $param")
}


// region other service
class OtherService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun otherFunction() {
        context(log) {
            // use the context log to perform an operation
            performOperation("other parameter")
        }
    }
}
// endregion


 */