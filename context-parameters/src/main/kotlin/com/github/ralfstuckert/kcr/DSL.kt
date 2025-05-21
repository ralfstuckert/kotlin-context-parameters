package com.github.ralfstuckert.kcr

import java.time.LocalDate

val oldSchedule = dsl {
    schedule {
        + LocalDate.of(2023, 10, 1)
        + LocalDate.of(2023, 10, 12)
    }
}

val newSchedule = dsl {
    schedule {
        -  1 Oktober 2023
        - 12 Oktober 2023
    }
}


context(_:Schedule)
infix fun Int.Oktober(year: Int): LocalDate =
    LocalDate.of(year, 10,this)

context(_:Schedule)
infix fun Int.Dezember(year: Int): LocalDate =
    LocalDate.of(year, 12,this)

context(schedule:Schedule)
operator fun LocalDate.unaryMinus():Boolean =
    with (schedule) {
        + this@unaryMinus
        true
    }

