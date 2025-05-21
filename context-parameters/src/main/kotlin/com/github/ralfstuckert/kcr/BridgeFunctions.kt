package com.github.ralfstuckert.kcr

import com.github.ralfstuckert.kcr.DSL


context(dsl:DSL)
fun doSomethingWithDSL() {
    schedule {
        - 6  Dezember 2025
    }
}

context(dsl:DSL)
fun schedule(init: Schedule.() -> Unit) = dsl.schedule(init)

