package com.github.ralfstuckert.kcr


interface View {
    val resources: Resources
        get() = Resources()
}

class Resources {
    val displayMetrics = DisplayMetrics()
}

class DisplayMetrics {
    val density: Float = 1.5f
}