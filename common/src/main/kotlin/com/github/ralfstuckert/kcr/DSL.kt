package com.github.ralfstuckert.kcr

import java.time.LocalDate

fun dsl(init: DSL.() -> Unit): DSL =
    DSL().apply(init)

class DSL {
    private val schedule = Schedule()
    fun schedule(init: Schedule.() -> Unit) =
        schedule.init()
}

class Schedule() {
    private val dates:MutableList<LocalDate> = mutableListOf<LocalDate>()
    operator fun LocalDate.unaryPlus() {
        dates.add(this)
    }
}

