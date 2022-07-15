package com.posadeus.rover.domain

sealed class Error

object CommandNotFound : Error()
object MissionAborted : Error()

data class WrongCoordinate(val message: String) : Error()
