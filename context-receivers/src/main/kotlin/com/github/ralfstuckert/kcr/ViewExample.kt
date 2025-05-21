package com.github.ralfstuckert.kcr


class MyView : View {
    val height = 8.dp

    val Int.dp: Float
        get() = this.toFloat() * resources.displayMetrics.density

}

/*

val (Float, View).dp get() = this@Float * this@View.resources.displayMetrics.density

 */

context(View)
val Float.dp
    get() = this * resources.displayMetrics.density
// explicitly written this@Float * this@View.resources.displayMetrics.density

context(View)
val Int.dp
    get() = this.toFloat().dp




