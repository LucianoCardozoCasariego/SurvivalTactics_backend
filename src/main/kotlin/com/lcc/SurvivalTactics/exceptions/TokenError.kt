package com.lcc.SurvivalTactics.exceptions

class TokenError(val text: String): RuntimeException() {
    override val message: String
        get() = text
}