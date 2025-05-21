# Bridge Functions
One (of the) complaint(s) that came with the change from context receivers to parameters, is that you have to name them
in order to use them. This might be undesirable in certain situations like DSLs. Take the following function using the
DSL from above that works fine with former context receivers:

```kotlin
context(DSL)
fun doSomethingWithDSL() {
    schedule { 
        - 6  Dezember 2025
    }
}
```

With context parameters, you have to explicitly name the DSL parameter and reference it; there is no implicit receiver:

```kotlin
context(dsl:DSL)
fun doSomethingWithDSL() {
    dsl.schedule {
        - 6  Dezember 2025
    }
}
```

To come around this situation, the Keep suggests writing a so-called bridge function, which does the dereferencing
for you:

[BridgeFunctions.kt](../blob/main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/BridgeFunctions.kt):

```kotlin
context(dsl:DSL)
fun schedule(init: Schedule.() -> Unit) = dsl.schedule(init)

context(dsl:DSL)
fun doSomethingWithDSL() {
    schedule {
        - 6  Dezember 2025
    }
}
```

This is tedious, but if you need that, there is at least a workaround.

