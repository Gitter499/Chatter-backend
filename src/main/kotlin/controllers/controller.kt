package controllers

import io.javalin.http.Context


// Shared controller attributes
interface controller {
    fun getData(ctx: Context): Unit
}