package com.github.ralfstuckert.kcr



fun <A, R> context(a: A, block: context(A) () -> R): R = block(a)

fun <A, B, R> context(a: A, b: B, block: context(A, B) () -> R): R = block(a, b)

fun <A, B, C, R> context(a: A, b: B, c: C, block: context(A, B, C) () -> R): R = block(a, b, c)

context(ctx: A) fun <A> implicit(): A = ctx