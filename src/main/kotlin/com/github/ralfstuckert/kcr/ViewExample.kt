package com.github.ralfstuckert.kcr

context(view:View)
val Float.dp get() = this * view.resources.displayMetrics.density

context(_:View)
val Int.dp get() = this.toFloat().dp



class MyView : View {
    val height = 8.dp

    /*
    val Int.dp: Float
        get() = this.toFloat() * resources.displayMetrics.density

     */
}

