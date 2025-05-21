package com.github.ralfstuckert.kcr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServiceA {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun doSomething() {
        logger.info("Doing something in ServiceA")
        // Your logic here
    }
}