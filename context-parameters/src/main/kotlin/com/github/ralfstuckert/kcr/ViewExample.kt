package com.github.ralfstuckert.kcr

context(view:View)
fun Float.dp()= this * view.resources.displayMetrics.density

context(_:View)
fun Int.dp() = this.toFloat().dp()


// or even more readable as extension properties

context(view:View)
val Float.dp
    get() = this * view.resources.displayMetrics.density

context(_:View)
val Int.dp
    get() = this.toFloat().dp


class SomeView : View {
    val someDimension = 4.dp
}


