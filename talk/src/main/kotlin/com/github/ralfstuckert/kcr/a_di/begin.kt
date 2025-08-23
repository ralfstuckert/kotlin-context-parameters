package com.github.ralfstuckert.kcr.a_di

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SomeService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun someFunction() =  {
        performOperation("some parameter")
    }

    fun performOperation(param: String) {
        // do some operation and log result
        log.info("successfully performed operation with param: $param")
    }
}


// region other service
class OtherService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun otherFunction() {
//        performOperation("other parameter")
    }
}
// endregion