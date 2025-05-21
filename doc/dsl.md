# DSLs

Another use case for context parameters is to create resp. extends DSLs. Let's take a look at the following example, that
let us create some kind of schedule.

[DSL.kt](../blob/main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/DSL.kt):
```kotlin
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

...
  dsl {
   schedule {
    + LocalDate.of(2023, 10, 2)
    + LocalDate.of(2023, 10, 13)
     ...
   }
```

We'd like to enhance the readability of the DSL by allowing to add the dates in more natural way. Also, we'd like it to be
represented with dashes instead of plus signs. This could easily be done by adding (infix) operators to the `Schedule` class,
but unfortunately, the Schedule class is an external class, and we don't have access to the source code. For sure we could
write some global infix operators, but that would unnecessarily pollute our code base, we want those operators to be only
present in the context of the DSL. So we can use context parameters to add the operators to the `Schedule` class. I
prefer the german date format, so I will add infix operators for each month, and a unary minus for the dashes:

```kotlin
context(_:Schedule)
infix fun Int.Oktober(year: Int): LocalDate =
    LocalDate.of(year, 10,this)
//...additional operators for the other month

context(schedule:Schedule)
operator fun LocalDate.unaryMinus():Boolean =
   with (schedule) {
      + this@unaryMinus
      true
   }
```

Now I can use the DSL like this:

```kotlin
  dsl {
    schedule { 
        - 2  Oktober 2023
        - 13 Oktober 2023
     ...
    }
```

Next: [Bridge Functions](doc/bridge_functions.md)