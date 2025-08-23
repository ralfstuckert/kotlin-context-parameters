package com.github.ralfstuckert.kcr.b_scoped_functions

data class User(val name: String)

interface Transaction

// region DbTransaction
class DbTransaction : Transaction {
    fun begin(): Unit = TODO()
    fun commit(): Unit = TODO()
    fun rollback(): Unit = TODO()
}
// endregion 

// region DbRepository
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

    fun save(entity: E): E = TODO()
}
// endregion


class ServiceA(val repository: DbRepository<User>) {

    fun perform() {
        doSomething()
    }

    fun doSomething() {
        // some operations
        // repository.xyz()
        // ...
        doSomeMoreStuff()
    }

    fun doSomeMoreStuff() {
        val user = User("Herbert")
        repository.save(user)
    }

}




