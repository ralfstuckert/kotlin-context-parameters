package com.github.ralfstuckert.kcr


interface Transaction

class DbTransaction : Transaction {
    fun begin(): Unit = TODO()
    fun commit(): Unit = TODO()
    fun rollback(): Unit = TODO()
}

class DbRepository<E> {

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

    context(_: Transaction)
    fun save(entity: E): E = TODO()
}


class ServiceA(val repository: DbRepository<User>) {

    fun perform() =  repository.transactional {
        doSomething()
    }

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


}




