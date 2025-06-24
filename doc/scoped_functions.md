# Scoped Functions

Some functions are only valid in a certain scope, e.g. when dealing with a database transaction. And wouldn't it be nice
if we could let the compiler check this for us? Let's take a look at the following example. We have a function `doSomething()`
that is only valid in the scope of a transaction, so we require it to be called with a context parameter `Transactional`.
In our case here, this is just a marker interface, and since we don't use it in our code, it can be anonymous. The `doSomething()`
function calls another function `doSomeMoreStuff()`, which is supposed to be executed in the same transaction.

[Transactions.kt](../main/context-parameters/src/main/kotlin/com/github/ralfstuckert/kcr/Transactions.kt):
```kotlin
interface Transaction

context(_: Transaction)
fun doSomething() {
    // some operations
    // ...
 doSomeMoreStuff()
}

context(_: Transaction)
fun doSomeMoreStuff() {
    // some operations
    // ...
}
```

If we now try to call `doSomething()` without a transaction, the compiler will complain:

```kotlin
    doSomething() // "No context argument for 'Transaction' found."
```

Let's say we have a database repository that supports transactions. We could write a function `transactional()` that takes a
block of code and execute it in the context of a transaction:

```kotlin
class DbTransaction : Transaction {
    fun begin(): Unit = TODO()
    fun commit(): Unit = TODO()
    fun rollback(): Unit = TODO()
}

class DbRepository {

    fun <T> transactional(block: context(Transaction) () -> T): T =
        with(DbTransaction()) {
            begin()
            return try {
                val result = block(this)
                commit()
                result
            } catch (e: Exception) {
                rollback()
                throw e
            }
        }
 ...
}
```

Watch the type of the block parameter `context(Transaction) () -> T`, which means that the given block
must be called in the context of a `Transaction`.

Now we can use the `transactional()` function to execute our code in the context of a transaction, and the compiler
is satisfied:

```kotlin
    val repository = DbRepository()
    val result = repository.transactional {
        doSomething()
    }
```

Next: [Externally Implemented Interfaces / Type Classes](type_classes.md)
