package com.lcc.SurvivalTactics.configuration

class SecurityRoutes {
    val publicRoutes = listOf(
        "/user/hello",
        "/user/login",
        "/user/register",
        "/error"
    )

    val adminRoutes = listOf(
        "/user/admin"
    )
}