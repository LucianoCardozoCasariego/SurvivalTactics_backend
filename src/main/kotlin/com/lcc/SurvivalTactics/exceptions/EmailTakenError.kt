package com.lcc.SurvivalTactics.exceptions

class EmailTakenError(val text: String): RuntimeException() {
    override val message: String
        get() = text
}
