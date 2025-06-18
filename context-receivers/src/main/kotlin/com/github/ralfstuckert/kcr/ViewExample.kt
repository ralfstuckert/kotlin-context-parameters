package com.github.ralfstuckert.kcr


class SomeView : View {
    val someDimension = 4f.dp()

    fun Float.dp()= this * resources.displayMetrics.density

}

/*

val (Float, View).dp get() = this@Float * this@View.resources.displayMetrics.density

 */

context(View)
fun Float.dp()= this * resources.displayMetrics.density

context(View)
fun Int.dp() = this.toFloat().dp()


// or even more readable as extension properties

context(View)
val Float.dp
    get() = this * resources.displayMetrics.density
// explicitly written this@Float * this@View.resources.displayMetrics.density

context(View)
val Int.dp
    get() = this.toFloat().dp



