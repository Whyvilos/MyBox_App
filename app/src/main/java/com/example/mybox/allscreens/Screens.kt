package com.example.mybox.allscreens

sealed class Screens(val route: String) {
    object SignUp : Screens("signup")
    object SignIn : Screens("signin")
    object Line : Screens("line")
    object Catalog : Screens("catalog")
    object Item : Screens("item")
    object Service : Screens("service")
    object Notices : Screens("notices")
    object OrdersList : Screens("orders_list")
    object OrdersOne : Screens("orders_one")
    object OrdersYou : Screens("your_orders")

    object Profile : Screens("profile")
    object Start : Screens("start")
    object Favorite : Screens("favorite")
    object Chat : Screens("chat")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                    arg ->
                append("/$arg")
            }
        }
    }
}
