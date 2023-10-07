package com.labwhisper.beerchallenge.navigation

private const val BEER_ID_SUFFIX = "{beerId}"
private const val BEER_LIST_SCREEN_ROUTE = "beer_list_screen"
private const val BEER_DETAIL_SCREEN_ROUTE = "beer_detail_screen"

enum class Screen(val route: String, val popupTo: Screen? = null) {
    List(route = BEER_LIST_SCREEN_ROUTE, popupTo = List),
    Detail(route = "$BEER_DETAIL_SCREEN_ROUTE/$BEER_ID_SUFFIX", popupTo = List);


    companion object {
        fun getByRoute(route: String): Screen? {
            return values().firstOrNull { screen ->
                when (screen) {
                    Detail -> route.startsWith(Detail.route.removeSuffix(BEER_ID_SUFFIX))
                    else -> screen.route == route
                }
            }
        }
    }
}
