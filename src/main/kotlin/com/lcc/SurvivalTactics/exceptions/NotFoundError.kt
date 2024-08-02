package com.lcc.SurvivalTactics.exceptions

class NotFoundError (val text: String): RuntimeException() {
    override val message: String
        get() = text
}